package edu.sjsu.cmpe275.lab2.controllers;


import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@RequestMapping(value ="/addToCart", method = RequestMethod.POST)
	public void issueBook(@RequestParam("bookIssue") int[] bookids, HttpSession session)
	{
		// test 
		System.out.println(bookids[0]);
		
		IssueDao issuDao = context.getBean(IssueDao.class);
		BookDao bookDao = context.getBean(BookDao.class);
		UserDao userDao = context.getBean(UserDao.class);

		Date date = new Date();
		System.out.println(date);
		long ltime=date.getTime() + 30*24*60*60*1000;
		Date today8 = new Date(ltime);
		System.out.println(today8);
		
		// create object list
		ArrayList<Issue> issueList = new ArrayList<Issue>();
		ArrayList<Book> bookList = new ArrayList<Book>();
		
		for(int bookid:bookids)
		{
			//issue object
			Issue issue = new Issue();
			issue.setBookId(bookid);
			issue.setDueDate( DateService.addDate(30));
			issue.setIssueDate(DateService.addDate(0));
			User user = (User) session.getAttribute("user");
			issue.setUserEmail(user.getEmail());
			issueList.add(issue);
			
			// book object
			Book book = bookDao.getBookById(bookid);
			book.setCopies_available(book.getCopies_available() - 1);
			bookList.add(book);
			
			
		}
		
		// user object
		User user = (User) session.getAttribute("user");
		user.setNoOfBooksIssued(user.getNoOfBooksIssued() + 1);
		
		issuDao.issueBook(issueList, bookList, user);
		
	}
}
