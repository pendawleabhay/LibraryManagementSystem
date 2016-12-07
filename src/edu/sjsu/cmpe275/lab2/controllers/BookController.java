package edu.sjsu.cmpe275.lab2.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.sjsu.cmpe275.lab2.dao.BookDao;
import edu.sjsu.cmpe275.lab2.dao.UserDao;
import edu.sjsu.cmpe275.lab2.entities.Book;
import edu.sjsu.cmpe275.lab2.entities.User;

@Controller
@RequestMapping(value = "/book")
public class BookController {

	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
	BookDao bookDao;
	
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
		ModelAndView model;
		User user = (User) session.getAttribute("user");
		
		if(user!=null){
			String querySearch = "select b from Book b where b." + searchType + " like '%" + searchString +"%'";
			List<Book> bookList = bookDao.getBookBySearchType(querySearch);
			
			model = new ModelAndView("/Book/SearchBook");
			if(bookList!=null) {
				model.addObject("bookList", bookList);
				model.addObject("user", user);
			} else
				model.addObject("message", "No Books Found for " + searchType + " = " + searchString +"!");
			
		} else {
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
		if(user!=null && user.getUserType().equals("librarian")){
			bookDao = context.getBean(BookDao.class);
			Book bookGet = bookDao.getBookById(bookid);
			
			if(bookGet != null) {
				bookDao.deleteBook(bookid);
			
				model = new ModelAndView("/User/LibrarianHomepage");
				model.addObject("message", "Book with bookid: " + bookid + " deleted!");
			} else {
				model = new ModelAndView("error");
				model.addObject("error","Book with bookid: " + bookid + " can not be found!");
			}
		} else {
			model = new ModelAndView("error");
			model.addObject("error","Please Log in as librarian before deleting any book!");
		}
		return model;
	}
}
