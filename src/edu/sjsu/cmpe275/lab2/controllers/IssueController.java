package edu.sjsu.cmpe275.lab2.controllers;


import java.sql.SQLException;
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
		ModelAndView model;
		int[] bookids = (int[]) session.getAttribute("issueCart");
		IssueDao issueDao = context.getBean(IssueDao.class);
		BookDao bookDao = context.getBean(BookDao.class);
		User user = (User) session.getAttribute("user");
		
		
		if(bookids==null || bookids.length<1){
			model = new ModelAndView("User/PatronHomepage");
			model.addObject("message", "No books in your cart to Checkout!");
			return model;
		}
			
		String query3 = "select count(i) from Issue i where i.userEmail='" + user.getEmail() + "' AND i.issueDate='" + DateService.addDate(0) +"'";
		int countToday = issueDao.countQuery(query3 );
		System.out.println("query" + query3);
		System.out.println("count today =" + countToday);
		
		int totalCount = issueDao.countQuery("select count(i) from Issue i where i.userEmail='" + user.getEmail() + "'" );
		System.out.println("totalcount" + totalCount);
		
		// check if already checked out more than 5 books for the day
		if(countToday + bookids.length >5)
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
			
			for(int bookid:bookids)
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
			int[] issueCart = (int[]) session.getAttribute("issueCart");
			user.setNoOfBooksIssued(user.getNoOfBooksIssued() + issueCart.length);
			
			// waitlist object
			ArrayList<Waitlist> waitlists = new ArrayList<Waitlist>();
			int[] waitlistCart = (int[]) session.getAttribute("waitlistCart");
			
			for(int tempBookId : waitlistCart)
			{
				Waitlist waitlist = new Waitlist();
				waitlist.setBookId(tempBookId);
				waitlist.setUserEmail(user.getEmail());
				waitlist.setWaitlistDate(DateService.addDate(0));
				waitlists.add(waitlist);
			}
			
			issueDao.checkout(issueList, bookList, user, waitlists);
					
	
			System.out.println("bookList Length: " + bookList.size());
			
			model = new ModelAndView("User/Checkout");
			model.addObject("bookList", bookList);
			model.addObject("dueDate", dueDate);
			
			// send Mail
			emailBody = emailBody.concat("<br><br>Due Date: " + dueDate);
			//Mail.generateAndSendEmail("Your Order", emailBody, user.getEmail());
		}
		
		int[] issueCart1 = (int[]) session.getAttribute("issueCart");
		System.out.println("cart" + issueCart1.length);
		int[] issueCart2={};
		session.setAttribute("issueCart", issueCart2);
		
		return model;
	}
	
	@RequestMapping(value ="/addToCart", method = RequestMethod.POST)
	public ModelAndView addToCart(@RequestParam("bookIssue") int[] bookIds, HttpSession session)
	{
		ModelAndView model;
		int[] issueCart = (int[]) session.getAttribute("issueCart");
		if(issueCart.length + bookIds.length >5 )
		{
			model = new ModelAndView("User/PatronHomepage");
			model.addObject("message", "You Cannot have more than 5 books in cart");
			
		}
		else
		{
			int[] newBookIds;
			if(issueCart.length == 0)
			{
				newBookIds = new int[bookIds.length];
			}
			else
			{
				newBookIds = new int[issueCart.length + bookIds.length];
			}
			int j=0;
			for(int i=0; i< issueCart.length ; i++)
			{
				newBookIds[j] = issueCart[i];
				j++;
			}
			System.out.println("j=" + j);
			System.out.println("bookid length" + bookIds.length);
			for(int i=0; i< issueCart.length ; i++)
			{
				newBookIds[j] = issueCart[i];
				j++;
			}
			for(int i=0; i< bookIds.length ; i++)
			{
				newBookIds[j] = bookIds[i];
				j++;
			}
			session.setAttribute("issueCart", newBookIds);
			model = new ModelAndView("User/PatronHomepage");
			model.addObject("message", bookIds.length + " books added to Cart");
			for(int tempbook:newBookIds)
			{
				System.out.println(tempbook);
			}
		}
		
		int[] issueCart1 = (int[]) session.getAttribute("issueCart");
		System.out.println("cart" + issueCart1.length);
		
		return model;
	}

	public ModelAndView addToWaitlist(@RequestParam("bookIssue") int[] bookIds, HttpSession session)
	{
		ModelAndView model;
		int[] waitlistCart = (int[]) session.getAttribute("waitlistCart");
		User user = (User) session.getAttribute("user");
		
		// 
		int[] newBookIds = null;
		int j=0;
		for(int i=0; i< waitlistCart.length ; i++)
		{
			newBookIds[j] = waitlistCart[i];
			j++;
		}
		for(int i=0; i< bookIds.length ; i++)
		{
			newBookIds[j] = bookIds[i];
			j++;
		}
		session.setAttribute("waitlistCart", newBookIds);
		model = new ModelAndView("User/PatronHomepage");
		model.addObject("message", bookIds.length + " books added to Cart");
		for(int tempbook:newBookIds)
		{
			System.out.println(tempbook);
		}
		int[] waitlistCart1 = (int[]) session.getAttribute("waitlistCart");
		System.out.println("cart" + waitlistCart1.length);
		
		return model;
	}

	
}
