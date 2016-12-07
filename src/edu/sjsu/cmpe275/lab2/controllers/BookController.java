package edu.sjsu.cmpe275.lab2.controllers;

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
	
	/*@RequestMapping(value = "/test", method = RequestMethod.POST)
	public ModelAndView addBook1(@ModelAttribute("book") Book book)
	{
		ModelAndView model = new ModelAndView("error");
		System.out.println("testing book controller");
		System.out.println("Book Name: " + book.getAuthor());
		model.addObject("error", "Testing Done");
		return model;
	}*/
	
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
				model = new ModelAndView("redirect:/book/update?bookid=" + bookGet.getBookid() + "&book=" + bookGet);
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
	
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView update(
			@RequestParam("book") Book book,
			HttpSession session){
		
		ModelAndView model;
		System.out.println("in update controller");
		User user = (User) session.getAttribute("user");
		if(user!=null && user.getUserType().equals("librarian")) {
			System.out.println("in if user!=null");
			model = new ModelAndView("/Book/UpdateBook");
			//model.addObject("errorMessage", "Book Title already exist");
			model.addObject("user",user);
			model.addObject("book", book);
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
}
