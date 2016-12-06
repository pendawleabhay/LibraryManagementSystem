package edu.sjsu.cmpe275.lab2.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.sjsu.cmpe275.lab2.dao.PatronDao;
import edu.sjsu.cmpe275.lab2.entities.Book;
import edu.sjsu.cmpe275.lab2.entities.Patron;

@Controller
@RequestMapping(value = "/book")
public class BookController {
ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
	
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
			@ModelAttribute("book") Book book
			/*@RequestParam("author") String author, 
			@RequestParam("title") String title,
			@RequestParam("call_number") String call_number,
			@RequestParam("publisher") String publisher,
			@RequestParam("year_of_publication") int year_of_publication,
			@RequestParam("location_in_library") String location_in_library,
			@RequestParam("number_of_copies") String number_of_copies,
			@RequestParam("current_status") String current_status,
			@RequestParam("created_by") String created_by*/
			)
	{
		ModelAndView model;
		model = new ModelAndView("");
		/*PatronDao dao = context.getBean(PatronDao.class);
		Patron patron = dao.getPatron(email);
		ModelAndView model;
		if(patron.getPassword().equals(password) && patron.getIsVerified()==1)
		{
			model = new ModelAndView("DisplayMessage");
			model.addObject("msg", "login successfull");
		}
		else
		{
			model = new ModelAndView("DisplayMessage");
			model.addObject("msg", "unsuccessfull login");
		}*/
		return model; 
	}
}
