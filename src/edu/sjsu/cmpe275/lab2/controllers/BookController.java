package edu.sjsu.cmpe275.lab2.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.servlet.http.HttpSession;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
//import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
/*import com.google.api.services.books.Books.Volumes.List;*/
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;

import edu.sjsu.cmpe275.lab2.dao.BookDao;
import edu.sjsu.cmpe275.lab2.dao.IssueDao;
import edu.sjsu.cmpe275.lab2.dao.UserDao;
import edu.sjsu.cmpe275.lab2.dao.WaitlistDao;
import edu.sjsu.cmpe275.lab2.entities.Book;
import edu.sjsu.cmpe275.lab2.entities.Issue;
import edu.sjsu.cmpe275.lab2.entities.User;
import edu.sjsu.cmpe275.lab2.entities.Waitlist;
import edu.sjsu.cmpe275.lab2.logic.DateService;
import edu.sjsu.cmpe275.lab2.logic.Mail;

@Controller
@RequestMapping(value = "/book")
public class BookController {

	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
	private static final String APPLICATION_NAME = "cmpe275";
	BookDao bookDao;
	IssueDao issueDao;
	UserDao userDao;
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(HttpSession session)
	{
		ModelAndView model;
		if(session.getAttribute("user")!=null){
			model = new ModelAndView("Book/AddBook");
			return model;
		} else {
			model = new ModelAndView("error");
			model.addObject("error","Please Log in before adding a book!");
		}
		return model; 
	}

	@RequestMapping(value = "/addBook", method = RequestMethod.POST)
	public ModelAndView addBook(
			@ModelAttribute("book") Book book,
			HttpSession session
			)
	{
		System.out.println("in addBook controller");
		//Printing all book values for testing
		System.out.println("book author: " + book.getAuthor());
		System.out.println("title: " + book.getTitle());
		System.out.println("call - number: " + book.getCall_number());
		System.out.println("publisher: " + book.getPublisher());
		System.out.println("year of publication: " + book.getYear_of_publication());
		System.out.println("location: " + book.getLocation_in_library());
		System.out.println("number of copies: " + book.getNumber_of_copies());
		System.out.println("copies available: " + book.getCopies_available());
		System.out.println("current status: " + book.getCurrent_status());
		System.out.println("keywords: " + book.getKeywords());
		
		
		ModelAndView model;
		User user = (User) session.getAttribute("user");
		//if the user type is librarian then do the operation of adding or updating book
		if(user!=null && user.getUserType().equals("librarian")){	
			//if book title exist, redirect the librarian to book update page
			// check if book title exists
			bookDao = context.getBean(BookDao.class);
			Book bookGet = bookDao.getBookByTitle("select b from Book b where b.title='" + book.getTitle() + "'");
			if(bookGet != null) {
				//model = new ModelAndView("redirect:/book/update?bookid=" + bookGet.getBookid());
				model = new ModelAndView("/Book/UpdateBook");
				//model.addObject("errorMessage", "Book Title already exist");
				model.addObject("book", bookGet);
				model.addObject("user",user);
				//this.updateBook(bookGet, session);
				//model.addObject("errorMessage", "Book Title already exist");
				//model.addObject("book",bookGet);
			} else {
				book.setCreated_by(user.getEmail());
				book.setUpdated_by(user.getEmail());
				bookDao.createBook(book);
				model = new ModelAndView("/User/LibrarianHomepage");
				model.addObject("message","Book with title '" + book.getTitle() + "' added!");
			}
		} else {
			model = new ModelAndView("error");
			model.addObject("error","Please Log in as a Librarian before adding a book!");
		}
		
		return model;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView update(
			@RequestParam("bookid") String bookidStr,
			HttpSession session){
		System.out.println("bookidStr: " + bookidStr);
		int bookid = Integer.parseInt(bookidStr);
		ModelAndView model;
		System.out.println("in update controller");
		System.out.println("bookid: " + bookid);
		User user = (User) session.getAttribute("user");
		if(user!=null && user.getUserType().equals("librarian")) {
			System.out.println("in if user!=null");
			bookDao = context.getBean(BookDao.class);
			Book bookGet = bookDao.getBookById(bookid);
			
			if(bookGet != null) {
				model = new ModelAndView("/Book/UpdateBook");
				//model.addObject("errorMessage", "Book Title already exist");
				model.addObject("user",user);
				model.addObject("book", bookGet);
			} else {
				model = new ModelAndView("error");
				model.addObject("error","Wrong Book ID!");
			}
		}else {
			model = new ModelAndView("error");
			model.addObject("error","Wrong URL!");
		}
		return model;
	}
	
	@RequestMapping(value = "/updateBook", method = RequestMethod.POST)
	public ModelAndView updateBook(
			@ModelAttribute("book") Book book,
			HttpSession session
			)
	{
		System.out.println("in updateBook controller");
		//Printing all book values for testing
		System.out.println("book id: " + book.getBookid());
		System.out.println("book author: " + book.getAuthor());
		System.out.println("title: " + book.getTitle());
		System.out.println("call - number: " + book.getCall_number());
		System.out.println("publisher: " + book.getPublisher());
		System.out.println("year of publication: " + book.getYear_of_publication());
		System.out.println("location: " + book.getLocation_in_library());
		System.out.println("number of copies: " + book.getNumber_of_copies());
		System.out.println("copies available: " + book.getCopies_available());
		System.out.println("current status: " + book.getCurrent_status());
		System.out.println("keywords: " + book.getKeywords());
		
		
		ModelAndView model;
		User user = (User) session.getAttribute("user");
		//if the user type is librarian then do the operation of adding or updating book
		if(user!=null && user.getUserType().equals("librarian")){	
			//if book title exist, redirect the librarian to book update page
			// check if book title exists
			bookDao = context.getBean(BookDao.class);
			reserveBook(book);
			book = bookDao.getBookById(book.getBookid());
			book.setUpdated_by(user.getEmail());
			bookDao.updateBook(book);
			model = new ModelAndView("/User/LibrarianHomepage");
			model.addObject("message","Book with title '" + book.getTitle() + "' and Book ID=" + book.getBookid() + " updated!");
		} else {
			model = new ModelAndView("error");
			model.addObject("error","Please Log in as a Librarian before adding a book!");
		}
		
		return model;
	}
	
	

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search(
			@RequestParam("searchType") String searchType,
			@RequestParam("searchString") String searchString,
			HttpSession session
			) {
		
		System.out.println("searchType: " + searchType);
		System.out.println("searchString: " + searchString);
		
		bookDao = context.getBean(BookDao.class);
		issueDao = context.getBean(IssueDao.class);
		ModelAndView model;
		User user = (User) session.getAttribute("user");
		
		if(user!=null){
			String querySearch;
			//checking if search type is current_status then convert string to boolean values
			if(searchType.equals("current_status")){
				if(searchString.toLowerCase().equals("available"))
					querySearch = "select b from Book b where b.current_status=1";
				else
					querySearch = "select b from Book b where b.current_status=0";
			}
			//checking if search type is year_of_publication || copies_available || number_of_copies then convert to int
			else if(searchType.equals("year_of_publication") || searchType.equals("copies_available") || searchType.equals("number_of_copies"))
				querySearch = "select b from Book b where b." + searchType + " = " + Integer.parseInt(searchString);
			else if(searchType.equals("created_by") || searchType.equals("updated_by"))
				querySearch = "select b from Book b where b." + searchType + " = '" + searchString +"'";
			else 
				querySearch = "select b from Book b where b." + searchType + " like '%" + searchString +"%'";
			
			List<Book> searchResultBookList = bookDao.getBookBySearchType(querySearch);
			
			model = new ModelAndView("/Book/SearchBook");
			if(searchResultBookList!=null && searchResultBookList.size()>=1) 
			{
				WaitlistDao waitlistDao = context.getBean(WaitlistDao.class);
				List<Book> updatedBooksList = new ArrayList<Book>();
				for(Book tempBook : searchResultBookList)
				{
					if (tempBook.getReserved_till()==null)
					{
						
					}
					else if(tempBook.getReserved_till().compareTo(DateService.getInstance().addDateToAppDate(0))>=0)
					{
						List<Waitlist> waitlists = waitlistDao.getHighestWaitlist(tempBook.getBookid(), tempBook.getCopies_available());
						boolean exist = false;
						for(Waitlist tempWaitlist : waitlists)
						{
							System.out.println("tempWaitlist" + tempWaitlist.getUserEmail());
							System.out.println("user" + user.getEmail());
							if(user.getEmail().equals(tempWaitlist.getUserEmail()))
							{
								exist = true;
							}
						}
						if(!exist)
						{
							tempBook.setIsReserved(1);
						}
					}
					
					updatedBooksList.add(tempBook);
				}
				model.addObject("bookList", updatedBooksList);
				if(user.getUserType().equals("patron"))
				{
					querySearch = "select i.bookId from Issue i where i.userEmail='" + user.getEmail() + "'";
					System.out.println(querySearch);
					List<Integer> userBooksList = new ArrayList<Integer>();
					userBooksList = (List<Integer>) issueDao.getBookIds(querySearch);
					List<Boolean> userBookList = new ArrayList<Boolean>();
					List<Waitlist> userWaitlist = waitlistDao.getWaitlistByUserEmail(user.getEmail());
					//Sending Boolean list of values which contain issued books to the users as true, otherwise false
					if(userBooksList!=null && userBooksList.size()>0)
					{
						for(int index1=0;index1<updatedBooksList.size();index1++)
						{
							userBookList.add(index1, false);
							for(int index2=0;index2<userBooksList.size(); index2++)
							{
								if(updatedBooksList.get(index1).getBookid()==userBooksList.get(index2))
								{
									userBookList.set(index1, true);
								}
							}
							for(int index3=0;index3<userWaitlist.size(); index3++)
							{
								if(updatedBooksList.get(index1).getBookid()==userWaitlist.get(index3).getBookId())
								{
									userBookList.set(index1, true);
								}
							}
						}
					}
					
					model.addObject("userBookList", userBookList);
				}
				model.addObject("user", user);
			} 
			else
			{
				model.addObject("message", "No Books Found for " + searchType + " = " + searchString +"!");
				System.out.println("message: No Books Found for " + searchType + " = " + searchString +"!" );
			}
		} 
		else 
		{
			model = new ModelAndView("error");
			model.addObject("error","Please Log in before searching for book!");
		}
		
		return model;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ModelAndView delete(
			@RequestParam(name = "bookid") String bookidStr,
			HttpSession session){
		ModelAndView model;
		System.out.println("bookidStr: " + bookidStr);
		int bookid = Integer.parseInt(bookidStr);
		System.out.println("in delete book controller");
		System.out.println("bookid: " + bookid);
		User user = (User) session.getAttribute("user");
		if(user!=null && user.getUserType().equals("librarian"))
		{
			bookDao = context.getBean(BookDao.class);
			Book bookGet = bookDao.getBookById(bookid);
			
			if(bookGet == null) 
			{
				model = new ModelAndView("error");
				model.addObject("error","Book with bookid: " + bookid + " can not be found!");
			}
			else if(bookGet.getNumber_of_copies() != bookGet.getCopies_available())
			{
				model = new ModelAndView("error");
				model.addObject("error","Book with bookid: " + bookid + " is checked out!");
			}
			else 
			{
				bookDao.deleteBook(bookid);
			
				model = new ModelAndView("/User/LibrarianHomepage");
				model.addObject("message", "Book with bookid: " + bookid + " deleted!");
			} 
		} 
		else 
		{
			model = new ModelAndView("error");
			model.addObject("error","Please Log in as librarian before deleting any book!");
		}
		return model;
	}
	
	@RequestMapping(value = "/return", method = RequestMethod.POST)
	public ModelAndView returnBook(
			@RequestParam(name = "bookid") String[] bookStr,
			HttpSession session){
		ModelAndView model;
		
		if(bookStr!=null && bookStr.length > 0) {
		
			Integer[] bookidList = new Integer[bookStr.length];
			
			for(int index = 0; index < bookStr.length; index++){
				System.out.println("bookStr[" + index + "]:" + bookStr[index]);
				bookidList[index] = Integer.parseInt(bookStr[index]);
			}
			
			User user = (User)session.getAttribute("user");
			if(user!=null && user.getUserType().equals("patron")) {
				
				bookDao = context.getBean(BookDao.class);
				issueDao = context.getBean(IssueDao.class);
				userDao = context.getBean(UserDao.class);
				
				//old logic
				//for(int index = 0 ; index < bookidList.length; index++) {
					
					/*//Removing Issue
					Issue issue = issueDao.getIssueById("select i from Issue i where i.bookId=" + bookidList[index] + " and i.userEmail='" + user.getEmail() + "'");
					if(issue!=null)
						issueDao.deleteIssue(issue.getIssueId());
					else
						System.out.println("No Issue Found");*/
					
					/*//Update Book
					Book book = bookDao.getBookById(bookidList[index]);
					if(book!=null){
						book.setCopies_available(book.getCopies_available()+1);
						bookDao.updateBook(book);
					}*/
				//}
			
				//New Logic
				//Removing Issue
				String bookidQuery = "";
				
				for(int index = 0; index < bookidList.length; index++){
					if(index == (bookidList.length-1))
						bookidQuery += bookidList[index];
					else 
						bookidQuery += bookidList[index] + ",";
				}
				
				List<Issue> issueList = issueDao.getIssuesById("select i from Issue i where i.bookId in (" + bookidQuery + ") and i.userEmail='" + user.getEmail() + "'");
				int rowsDeleted = 0;
				if(issueList!=null) {
					String issueidQuery = String.valueOf(issueList.get(0).getIssueId());
					for(int index = 1;index < issueList.size(); index++){
						issueidQuery += "," + String.valueOf(issueList.get(index).getIssueId());
					}
					//Deleting Issues
					rowsDeleted = issueDao.deleteIssues("delete from Issue i where i.issueId in (" + issueidQuery + ")");
				} else
					System.out.println("No Issues Found");
				
				//Update User
				user.setNoOfBooksIssued(user.getNoOfBooksIssued()-bookidList.length);
				userDao.updateUser(user);
				session.setAttribute("user", user);
				
				//Update Book
				String updateBooksCopiesAvailableQuery = "update Book b set b.copies_available=b.copies_available-1 where b.bookid in (" + bookidQuery + ")";
				int rowsUpdated = bookDao.updateBooks(updateBooksCopiesAvailableQuery);
				
				//Returning
				model = new ModelAndView("/User/PatronHomepage");
				if(rowsDeleted == bookidList.length && rowsUpdated == bookidList.length)
					model.addObject("message","Books has been Returned!");
				else
					model.addObject("message","Books could not be Returned!");
			} else {
				model = new ModelAndView("error");
				model.addObject("message","Please Login as Patron to return Books!");
			}
		} else {
			model = new ModelAndView("error");
			model.addObject("message", "Please select books to return!");
		}
		/*System.out.println("BookidStr: " + bookStr);
		int bookid = Integer.parseInt(bookStr);
		System.out.println("BookId int: " + bookid);

		User user = (User)session.getAttribute("user");
		if(user!=null && user.getUserType().equals("patron")) {
			bookDao = context.getBean(BookDao.class);
			issueDao = context.getBean(IssueDao.class);
			userDao = context.getBean(UserDao.class);
			
			//Removing Issue
			Issue issue = issueDao.getIssueById("select i from Issue i where i.bookId=" + bookid + " and i.userEmail='" + user.getEmail() + "'");
			if(issue!=null)
				issueDao.deleteIssue(issue.getIssueId());
			else
				System.out.println("No Issue Found");
			
			//Update Book
			Book book = bookDao.getBookById(bookid);
			if(book!=null){
				book.setCopies_available(book.getCopies_available()+1);
				bookDao.updateBook(book);
			}
				
			//Update User
			user.setNoOfBooksIssued(user.getNoOfBooksIssued()-1);
			userDao.updateUser(user);
			session.setAttribute("user", user);
			
			model = new ModelAndView("/User/PatronHomepage");
			model.addObject("message","Book has been Returned!");
		}*/
		return model;
	}
	
	@RequestMapping(value = "/renewBooks", method = RequestMethod.POST)
	public ModelAndView renewBook(
			@RequestParam(name = "bookid") String[] bookStr,
			HttpSession session){
		ModelAndView model;
		
		if(bookStr!=null && bookStr.length > 0) {
		
			Integer[] bookidList = new Integer[bookStr.length];
			
			for(int index = 0; index < bookStr.length; index++){
				System.out.println("bookStr[" + index + "]:" + bookStr[index]);
				bookidList[index] = Integer.parseInt(bookStr[index]);
			}
			
			User user = (User)session.getAttribute("user");
			if(user!=null && user.getUserType().equals("patron")) {
				
				bookDao = context.getBean(BookDao.class);
				issueDao = context.getBean(IssueDao.class);
				userDao = context.getBean(UserDao.class);
				
				for(int index = 0 ; index < bookidList.length; index++) {
					
					//Update Issue
					Issue issue = issueDao.getIssueById("select i from Issue i where i.bookId=" + bookidList[index] + " and i.userEmail='" + user.getEmail() + "'");
					if(issue!=null){
						int renewalCount = issue.getRenewalCount();
						if(renewalCount < 3 && renewalCount >=0){
							issue.setRenewalCount(issue.getRenewalCount()+1);
							issueDao.updateIssue(issue);
						} else
							System.out.println("Can not Renew this book!");
					} else
						System.out.println("No Issue Found");
					
					//Update Book
					Book book = bookDao.getBookById(bookidList[index]);
					if(book!=null){
						book.setCopies_available(book.getCopies_available()+1);
						bookDao.updateBook(book);
					}
						
					//Update User
					user.setNoOfBooksIssued(user.getNoOfBooksIssued()-1);
					userDao.updateUser(user);
					session.setAttribute("user", user);
				}
				
				model = new ModelAndView("/User/PatronHomepage");
				model.addObject("message","Books has been Renewed!");
			} else {
				model = new ModelAndView("error");
				model.addObject("message","Please Login as Patron to renew Books!");
			}
		} else {
			model = new ModelAndView("error");
			model.addObject("message", "Please select books to return!");
		}
		/*System.out.println("BookidStr: " + bookStr);
		int bookid = Integer.parseInt(bookStr);
		System.out.println("BookId int: " + bookid);

		User user = (User)session.getAttribute("user");
		if(user!=null && user.getUserType().equals("patron")) {
			bookDao = context.getBean(BookDao.class);
			issueDao = context.getBean(IssueDao.class);
			userDao = context.getBean(UserDao.class);
			
			//Removing Issue
			Issue issue = issueDao.getIssueById("select i from Issue i where i.bookId=" + bookid + " and i.userEmail='" + user.getEmail() + "'");
			if(issue!=null)
				issueDao.deleteIssue(issue.getIssueId());
			else
				System.out.println("No Issue Found");
			
			//Update Book
			Book book = bookDao.getBookById(bookid);
			if(book!=null){
				book.setCopies_available(book.getCopies_available()+1);
				bookDao.updateBook(book);
			}
				
			//Update User
			user.setNoOfBooksIssued(user.getNoOfBooksIssued()-1);
			userDao.updateUser(user);
			session.setAttribute("user", user);
			
			model = new ModelAndView("/User/PatronHomepage");
			model.addObject("message","Book has been Returned!");
		}*/
		return model;
	}

	
	private void reserveBook(Book book)
	{
		WaitlistDao waitlistDao = context.getBean(WaitlistDao.class);
		System.out.println("inside reserve book");
		Book oldBook = bookDao.getBookById(book.getBookid());
		if(oldBook.getCopies_available()==0 && book.getCopies_available()>0)
		{
			Date reservedTill = DateService.getInstance().addDateToAppDate(3);
			book.setReserved_till(reservedTill);
			bookDao.reserveBook(book);
			
			//send email to waitlisted patrons saying their book is now available
			List<Waitlist> waitlists = waitlistDao.getHighestWaitlist(book.getBookid(), book.getCopies_available());
			for(Waitlist waitlist : waitlists)
			{
				String subject = "Your book is now availble";
				String emailBody = "Book " + book.getTitle() + " is now availble and reserved for you till " + reservedTill;
				String to = waitlist.getUserEmail();
				Mail.generateAndSendEmail(subject, emailBody, to);
			}
		}		
	}
	
	@RequestMapping(value = "/addBookByISBN", method = RequestMethod.GET)
	public ModelAndView addBookByISBN(
			@RequestParam("isbn") String isbn,
			HttpSession session){
		ModelAndView model;
		System.out.println("ISBN: " + isbn);
		User user = (User) session.getAttribute("user");
		if(user!=null && user.getUserType().equals("librarian")){
			model = new ModelAndView("Book/AddBook");
			
			if(isbn!=null && isbn.length()>0){
				try {
					final Books books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), null)
					        .setApplicationName(APPLICATION_NAME)
					        .setGoogleClientRequestInitializer(new BooksRequestInitializer("AIzaSyD7g5gfVwIZdYrotjEU1rR4gTRLylt6m58"))
					        .build();
					
					//.setGoogleClientRequestInitializer(new BooksRequestInitializer(ClientCredentials.API_KEY))
					// Set query string and Google eBooks.
					String query = "isbn:" + isbn;
					com.google.api.services.books.Books.Volumes.List volumesList = books.volumes().list(query);
					// Execute the query.
				    Volumes volumes = volumesList.execute();
				    if (volumes.getTotalItems() == 0 || volumes.getItems() == null) {
				      System.out.println("No matches found.");
				      model.addObject("errorMessage", "Wrong ISBN!");
				      return model;
				    }
				    
				    /*System.out.println("volumes size: " + volumes.size());
				    List<Volume> volumeList = volumes.getItems();
				    Volume volume1 = volumeList.get(0);
				    Volume volume2 = volumeList.get(1);
				    Volume volume3 = volumeList.get(2);
				    
				    System.out.println("volume1:" + volume1.getVolumeInfo().getAuthors().toString());
				    System.out.println("volume2:" + volume2.getVolumeInfo().getAuthors().toString());
				    System.out.println("volume3:" + volume3.getVolumeInfo().getAuthors().toString());*/
				    Book bookAPI = new Book();
				 // Output results.
				    for (Volume volume : volumes.getItems()) {
				      Volume.VolumeInfo volumeInfo = volume.getVolumeInfo();
				      Volume.SaleInfo saleInfo = volume.getSaleInfo();
				      System.out.println("==========");
				      // Title.
				     
				     
				      System.out.println("Title: " + volumeInfo.getTitle());
				      // Author(s).
				      java.util.List<String> authors = volumeInfo.getAuthors();
				      String author = "";
				      if (authors != null && !authors.isEmpty()) {
				        System.out.print("Author(s): ");
				        for (int i = 0; i < authors.size(); ++i) {
				          System.out.print(authors.get(i));
				          author += authors.get(i);
				          if (i < authors.size() - 1) {
				            System.out.print(", ");
				            author += ", ";
				          }
				        }
				        System.out.println();
				      }
				      
				   // Keywords(s).
				      java.util.List<String> categories = volumeInfo.getCategories();
				      String keywords = "";
				      if (categories != null && !categories.isEmpty()) {
				        System.out.print("Category(s): ");
				        for (int i = 0; i < categories.size(); ++i) {
				          System.out.print(categories.get(i));
				          keywords += categories.get(i);
				          if (i < categories.size() - 1) {
				            System.out.print(", ");
				            keywords += ", ";
				          }
				        }
				        System.out.println();
				      }
				      
				      bookAPI.setAuthor(author);
				      bookAPI.setTitle(volumeInfo.getTitle());
					  bookAPI.setPublisher(volumeInfo.getPublisher());
					  bookAPI.setYear_of_publication(Integer.parseInt(volumeInfo.getPublishedDate()));
					  bookAPI.setKeywords(keywords);
					  bookAPI.setCall_number(null);
					  bookAPI.setCopies_available(0);
					  bookAPI.setCreated_by(user.getEmail());
					  bookAPI.setCurrent_status(0);
					  bookAPI.setIsReserved(0);
					  bookAPI.setLocation_in_library(null);
					  bookAPI.setNumber_of_copies(0);
					  bookAPI.setReserved_till(null);
					  bookAPI.setUpdated_by(user.getEmail());
					  
				      //System.out.println("VolumeInfo getCategories: " + volumeInfo.getCategories().toString());
				    }
				    System.out.println("Adding book");
				    model.addObject("book", bookAPI);
					
				} catch (GeneralSecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				model.addObject("errorMessage", "Wrong ISBN!");
			}
		} else {
			model = new ModelAndView("error");
			model.addObject("message", "Login by Librarian!");
		}
		
		return model;
	}

}
