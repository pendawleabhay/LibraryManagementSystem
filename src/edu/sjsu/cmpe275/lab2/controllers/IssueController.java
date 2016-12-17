package edu.sjsu.cmpe275.lab2.controllers;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.NestedServletException;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import edu.sjsu.cmpe275.lab2.dao.BookDao;
import edu.sjsu.cmpe275.lab2.dao.IssueDao;
import edu.sjsu.cmpe275.lab2.dao.UserDao;
import edu.sjsu.cmpe275.lab2.entities.Book;
import edu.sjsu.cmpe275.lab2.entities.Issue;
import edu.sjsu.cmpe275.lab2.entities.User;
import edu.sjsu.cmpe275.lab2.entities.Waitlist;
import edu.sjsu.cmpe275.lab2.logic.DateService;
import edu.sjsu.cmpe275.lab2.logic.Mail;

@Controller
@RequestMapping(value = "/issue")
public class IssueController
{
	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
	
	@RequestMapping(value ="/checkout")
	public ModelAndView checkout( HttpSession session)
	{
		// declarations
		ModelAndView model;
		int[] issueCart = (int[]) session.getAttribute("issueCart");
		int[] waitlistCart = (int[]) session.getAttribute("waitlistCart");
		IssueDao issueDao = context.getBean(IssueDao.class);
		BookDao bookDao = context.getBean(BookDao.class);
		User user = (User) session.getAttribute("user");
		
		// error if empty cart
		if(issueCart.length + waitlistCart.length <1){
			model = new ModelAndView("User/PatronHomepage");
			model.addObject("message", "No books in your cart to Checkout!");
			return model;
		}
		
		// declaration for cart size
		String query3 = "select count(i) from Issue i where i.userEmail='" + user.getEmail() + "' AND i.issueDate='" + DateService.addDate(0) +"'";
		int countToday = issueDao.countQuery(query3 );		
		int totalCount = issueDao.countQuery("select count(i) from Issue i where i.userEmail='" + user.getEmail() + "'" );
		
		// check if already checked out more than 5 books for the day
		if(countToday + issueCart.length >5)
		{
			model = new ModelAndView("User/PatronHomepage");
			model.addObject("message","You cannot issue more than 5 books for the day");
		}
		
		// check if total books issued not more than 10
		else if(totalCount>10)
		{
			model = new ModelAndView("User/PatronHomepage");
			model.addObject("message","You cannot issue more than 10 books");
		}
		else
		{
			// create issue and book list
			ArrayList<Issue> issueList = new ArrayList<Issue>();
			ArrayList<Book> bookList = new ArrayList<Book>();
			java.sql.Date dueDate =	null;
			
			String emailBody = "Your Order is";
			
			for(int bookid:issueCart)
			{
				// add issue object to list
				Issue issue = new Issue();
				issue.setBookId(bookid);
				dueDate = DateService.addDate(30);
				issue.setDueDate(dueDate );
				issue.setIssueDate(DateService.addDate(0));
				issue.setUserEmail(user.getEmail());
				issueList.add(issue);
				
				// add book object to list
				Book book = bookDao.getBookById(bookid);
				book.setCopies_available(book.getCopies_available() - 1);
				bookList.add(book);
				
				// add books to mail
				emailBody = emailBody.concat("<br><br> Book ID:" + book.getBookid() +
											"<br> Title:" + book.getTitle() + 
											"<br> Author:" + book.getAuthor()
											);
			}
	
			// user object
			//int[] issueCart = (int[]) session.getAttribute("issueCart");
			user.setNoOfBooksIssued(user.getNoOfBooksIssued() + issueCart.length);
			
			// waitlist object
			ArrayList<Waitlist> waitlists = new ArrayList<Waitlist>();
			ArrayList<Book> waitlistBooks = new ArrayList<Book>();	
			for(int tempBookId : waitlistCart)
			{
				Waitlist waitlist = new Waitlist();
				waitlist.setBookId(tempBookId);
				waitlist.setUserEmail(user.getEmail());
				waitlist.setWaitlistDate(new Timestamp(System.currentTimeMillis()));
				waitlists.add(waitlist);
				Book book = bookDao.getBookById(tempBookId);
				waitlistBooks.add(book);
			}
			
			issueDao.checkout(issueList, bookList, user, waitlists);
	
			model = new ModelAndView("User/Checkout");
			model.addObject("bookList", bookList);
			model.addObject(waitlistBooks);
			model.addObject("dueDate", dueDate);
			
			// send Mail
			emailBody = emailBody.concat("<br><br>Due Date: " + dueDate);
			//Mail.generateAndSendEmail("Your Order", emailBody, user.getEmail());
		}
		
		// empty carts after checkout
		int[] issueCart2={};
		session.setAttribute("issueCart", issueCart2);
		int[] waitlistCart2={};
		session.setAttribute("waitlistCart", waitlistCart2);
		
		return model;
	}
	
	@RequestMapping(value ="/addToCart", method = RequestMethod.POST)
	public ModelAndView addToCart(@RequestParam("bookIssue") int[] issueBookIds, HttpSession session)
	{
		ModelAndView model = new ModelAndView("User/PatronHomepage");
		int[] issueCart = (int[]) session.getAttribute("issueCart");
		
		// check if book already exists in cart
		if(exists(issueBookIds, issueCart))
		{
			model.addObject("message", "Book Already Present in cart");
		}
		//check if number of books in cart is not > 5
		else if(issueCart.length + issueBookIds.length >5 )
		{
			model.addObject("message", "You Cannot have more than 5 books in cart");
			
		}
		else
		{
			int[] newCart;
			if(issueCart.length == 0)
			{
				newCart = new int[issueBookIds.length];
			}
			else
			{
				newCart = new int[issueCart.length + issueBookIds.length];
			}
			int j=0;
			for(int i=0; i< issueCart.length ; i++)
			{
				newCart[j] = issueCart[i];
				j++;
			}
			System.out.println("j=" + j);
			System.out.println("bookid length" + issueBookIds.length);
			for(int i=0; i< issueCart.length ; i++)
			{
				newCart[j] = issueCart[i];
				j++;
			}
			for(int i=0; i< issueBookIds.length ; i++)
			{
				newCart[j] = issueBookIds[i];
				j++;
			}
			session.setAttribute("issueCart", newCart);
			model.addObject("message", issueBookIds.length + " book added to Cart");
			for(int tempbook:newCart)
			{
				System.out.println(tempbook);
			}
		}
		
		int[] issueCart1 = (int[]) session.getAttribute("issueCart");
		System.out.println("cart" + issueCart1.length);
		
		return model;
	}

	@RequestMapping(value ="/addToWaitlist", method = RequestMethod.POST)
	public ModelAndView addToWaitlist(@RequestParam("bookIssue") int[] waitlistBookIds, HttpSession session)
	{
		ModelAndView model = new ModelAndView("User/PatronHomepage");
		int[] waitlistCart = (int[]) session.getAttribute("waitlistCart");
		User user = (User) session.getAttribute("user");
		
		// check if book already exists in cart
		if(exists(waitlistBookIds, waitlistCart))
		{
			model.addObject("message", "Book Already Present in cart");
		}
		else
		{
			int[] newCart = new int[waitlistCart.length + waitlistBookIds.length];
			int j=0;
			for(int i=0; i< waitlistCart.length ; i++)
			{
				newCart[j] = waitlistCart[i];
				j++;
			}
			for(int i=0; i< waitlistBookIds.length ; i++)
			{
				newCart[j] = waitlistBookIds[i];
				j++;
			}
			
			
			session.setAttribute("waitlistCart", newCart);
			model.addObject("message", waitlistBookIds.length + " book added to Cart");
			int[] waitlistCart1 = (int[]) session.getAttribute("waitlistCart");
			System.out.println("waitlist cart lenght" + waitlistCart1.length);
		}
		return model;
	}
	
	private boolean exists(int[] firstArray, int[] secondArray)
	{
		for(int i : firstArray)
		{
			for(int j : secondArray)
			{
				if(i==j)
				{
					return true;
				}
			}
		}
		return false;
	}

	
}
