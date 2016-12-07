package edu.sjsu.cmpe275.lab2.controllers;


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

import edu.sjsu.cmpe275.lab2.dao.BookDao;
import edu.sjsu.cmpe275.lab2.dao.IssueDao;
import edu.sjsu.cmpe275.lab2.dao.UserDao;
import edu.sjsu.cmpe275.lab2.entities.Book;
import edu.sjsu.cmpe275.lab2.entities.Issue;
import edu.sjsu.cmpe275.lab2.entities.User;
import edu.sjsu.cmpe275.lab2.logic.DateService;

@Controller
@RequestMapping(value = "/issue")
public class IssueController
{
	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
	
	@RequestMapping(value ="/checkout")
	public ModelAndView checkout( HttpSession session)
	{
		int[] bookids = (int[]) session.getAttribute("sessionBookIds");
		
		IssueDao issueDao = context.getBean(IssueDao.class);
		BookDao bookDao = context.getBean(BookDao.class);
		
		// create issue and book list
		ArrayList<Issue> issueList = new ArrayList<Issue>();
		ArrayList<Book> bookList = new ArrayList<Book>();
		java.sql.Date dueDate =	null;
		
		for(int bookid:bookids)
		{
			// add issue object to list
			Issue issue = new Issue();
			issue.setBookId(bookid);
			dueDate = DateService.addDate(30);
			issue.setDueDate(dueDate );
			issue.setIssueDate(DateService.addDate(0));
			User user = (User) session.getAttribute("user");
			issue.setUserEmail(user.getEmail());
			issueList.add(issue);
			
			// add book object to list
			Book book = bookDao.getBookById(bookid);
			book.setCopies_available(book.getCopies_available() - 1);
			bookList.add(book);
		}
		
		// user object
		User user = (User) session.getAttribute("user");
		int[] sessionBookIds = (int[]) session.getAttribute("sessionBookIds");
		user.setNoOfBooksIssued(user.getNoOfBooksIssued() + sessionBookIds.length);
		
		issueDao.checkout(issueList, bookList, user);
		
		ModelAndView model = new ModelAndView("User/Checkout");
		model.addObject(bookList);
		model.addObject("dueDate", dueDate);
		return model;
	}
	
	@RequestMapping(value ="/addToCart", method = RequestMethod.POST)
	public ModelAndView addToCart(@RequestParam("bookIssue") int[] bookIds, HttpSession session)
	{
		ModelAndView model;
		int[] sessionBookIds={};
		if(session.getAttribute("sessionBookIds") != null)
		{
			sessionBookIds = (int[]) session.getAttribute("sessionBookIds");
		}
		System.out.println(sessionBookIds.length);
		if(sessionBookIds.length + bookIds.length >5 )
		{
			model = new ModelAndView("User/PatronHomepage");
			model.addObject("message", "You Cannot have more than 5 books in cart");
			
		}
		else
		{
			int[] newBookIds;
			if(sessionBookIds.length == 0)
			{
				newBookIds = new int[bookIds.length];
			}
			else
			{
				newBookIds = new int[sessionBookIds.length + bookIds.length];
			}
			int j=0;
			for(int i=0; i< sessionBookIds.length ; i++)
			{
				newBookIds[j] = sessionBookIds[i];
				j++;
			}
			System.out.println("j=" + j);
			System.out.println("bookid length" + bookIds.length);
			for(int i=0; i< bookIds.length ; i++)
			{
				newBookIds[j] = bookIds[i];
				j++;
			}
			session.setAttribute("sessionBookIds", newBookIds);
			model = new ModelAndView("User/PatronHomepage");
			model.addObject("message", "Books added to cart");
			for(int tempbook:newBookIds)
			{
				System.out.println(tempbook);
			}
		}
		
		return model;
	}
}
