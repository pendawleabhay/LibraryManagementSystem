package edu.sjsu.cmpe275.lab2.logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.sjsu.cmpe275.lab2.dao.BookDao;
import edu.sjsu.cmpe275.lab2.dao.IssueDao;
import edu.sjsu.cmpe275.lab2.dao.WaitlistDao;
import edu.sjsu.cmpe275.lab2.entities.Book;
import edu.sjsu.cmpe275.lab2.entities.Issue;
import edu.sjsu.cmpe275.lab2.entities.Waitlist;

public class DateService
{
	private static DateService dateService = null;
	 private Date appDate;
	 ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
	 
	 private DateService() 
	   { 
		 	appDate = new Date();
	   }
	 
	 public static DateService getInstance( ) 
	   {
		 if(dateService == null)
		 {
			 dateService = new DateService();
		 }
	      return dateService;
	   }
	
	 // sets app date to given date in date
	public void setDate(Date date)
	{
		Calendar cal = Calendar.getInstance();
	    cal.setTime(appDate);
	    int oldDate = cal.get(Calendar.DATE);
	    cal.setTime(date);
	    int newDate = cal.get(Calendar.DATE);
	   /* System.out.println("old year = " + oldDate);
	    System.out.println("new year = " + newDate);*/
		if(date.compareTo(appDate)>0 &&  newDate!=oldDate) //checking if future date is being set
	    {
	    	sendDueEmail(date);
	    	//System.out.println();
	    	WaitlistDao waitlistDao = new WaitlistDao();
	    	//waitlistDao.removeReserved(date);
	    }
		appDate=date;
	}
	 
	private void sendDueEmail(Date date)
	{
		IssueDao issueDao = context.getBean(IssueDao.class);
    	List<Issue> dueWaitlist =issueDao.getDueWaitlist(date);
    	BookDao bookDao = context.getBean(BookDao.class);
    	for(Issue issue : dueWaitlist)
    	{
    		Book book = bookDao.getBookById(issue.getBookId());
    		String subject = "Your Book is Due";
    		String emailBody = "Book " + book.getTitle() + " is due on " + issue.getDueDate();
    		String to = issue.getUserEmail();
    		Mail.generateAndSendEmail(subject, emailBody, to);
    	}
	}


	// returns addition of given number of days and app date
	public  java.sql.Date addDateToAppDate(int days)
	{
		Calendar cal = Calendar.getInstance();
	    cal.setTime(appDate);
	    cal.add(Calendar.DATE, days);
	    Date d1 = cal.getTime();
		return new java.sql.Date(d1.getTime());
	}
	
	public  java.sql.Date addDate(Date date, int days)
	{
		Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.DATE, days);
	    Date d1 = cal.getTime();
		return new java.sql.Date(d1.getTime());
	}
	
	// adds given number of seconds to App date
	public void addSecond(int second)
	{
		Calendar cal = Calendar.getInstance();
	    cal.setTime(appDate);
	    cal.add(Calendar.SECOND, second);
	    Date d = cal.getTime();
		setDate(d);
	}
	
	// sets app date to given string date
	public  Date stringToDate(String year, String month, String date)
	{
		// parse string to int
		int year1 = Integer.parseInt(year);
		int month1 = Integer.parseInt(month) - 1;
		int date1 = Integer.parseInt(date);
		
		// create date
		Calendar cal = Calendar.getInstance();
		cal.set(year1, month1, date1, 0, 0);
		Date date2 = cal.getTime();
		
		// set date
		return date2;
	}
	
	// returns current app date
	public Date getDate()
	{
		return appDate;
	}
	
	
}
