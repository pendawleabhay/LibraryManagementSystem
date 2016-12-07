package edu.sjsu.cmpe275.lab2.controllers;

import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.sjsu.cmpe275.lab2.dao.BookDao;
import edu.sjsu.cmpe275.lab2.dao.IssueDao;
import edu.sjsu.cmpe275.lab2.dao.UserDao;
import edu.sjsu.cmpe275.lab2.logic.Mail;
import edu.sjsu.cmpe275.lab2.entities.Book;
import edu.sjsu.cmpe275.lab2.entities.User;

@Controller
@RequestMapping(value = "/user")
public class UserController 
{
	
	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
	IssueDao issueDao;
	
	@RequestMapping(value="/getSignUp")
	public ModelAndView getUserSignup()
	{
		ModelAndView model = new ModelAndView("User/SignupForm");
		return model;
	}
	
	@RequestMapping(value="/getVerify")
	public ModelAndView getVerify()
	{
		ModelAndView model = new ModelAndView("User/verify");
		return model;
	}
	
	@RequestMapping(value = "/signIn", method = RequestMethod.POST)
	public ModelAndView signIn(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session)
	{
		UserDao dao = context.getBean(UserDao.class);
		User user = dao.getUser(email);
		ModelAndView model;
		if(user.getPassword().equals(password) && user.getIsVerified()==1)
		{
			/*model = new ModelAndView("DisplayMessage");
			model.addObject("msg", "login successfull");*/
			session.setAttribute("user", user);
			if(user.getUserType().equals("patron"))
			{
				model = new ModelAndView("User/PatronHomepage");
				BookDao dao1 = context.getBean(BookDao.class);
				Book book1 = dao1.getBookById(3);
				Book book2 = dao1.getBookById(2);
				model.addObject("book1", book1);
				model.addObject("book2", book2);
			}
			else
			{
				model = new ModelAndView("User/LibrarianHomepage");
			}
			model.addObject("user", user);
		}
		else
		{
			model = new ModelAndView("DisplayMessage");
			model.addObject("msg", "unsuccessfull login");
		}
		return model; 
	}
	
	@RequestMapping(value = "/signUp", method = RequestMethod.POST)
	public ModelAndView signUp(@ModelAttribute("user")  User user)
	{
		UserDao dao = context.getBean(UserDao.class);
		ModelAndView model;
		
		// check if sjsu id exists
		int count = dao.countQuery("select count(p) from User p where p.sjsuId=" + user.getSjsuId());
		if(count>0)
		{
			model = new ModelAndView("DisplayMessage");
			model.addObject("msg", "SJSU ID Already in use");
		}
		else
		{
			// generate verification code
			Random rand = new Random();
			int  code = rand.nextInt(999999) + 100000;
			user.setVerificationCode(code);
			
			// set user type 
			if(user.getEmail().contains("@sjsu.edu"))
			{
				user.setUserType("librarian");
			}
			else
			{
				user.setUserType("patron");
			}
			
			// save user
			dao.createUser(user);
			
			// send verification mail
			String subject = "Verify Your Library Account";
			String body = "Your library account verfication code is following.<br><br><h1>" + code + "</h1>";
			String to = user.getEmail();
			Mail.generateAndSendEmail(subject, body, to);
			
			
			/*model = new ModelAndView("DisplayMessage");
			model.addObject("msg", "sign up successfull");*/
			model = new ModelAndView("User/verify");
		}
		
		return model; 
	}
	
	@RequestMapping(value = "/verify", method = RequestMethod.POST)
	public ModelAndView verify(@RequestParam("email") String email, @RequestParam("code") int code) throws InterruptedException
	{
		UserDao dao = context.getBean(UserDao.class);
		User user = dao.getUser(email);
		ModelAndView model;
		if(user.getVerificationCode() == code)
		{
			user.setIsVerified(1);
			dao.updateUser(user);
			model = new ModelAndView("index");
			
			// send confirmation mail
			String subject = "Library Account Verified";
			String body = "Your library account is now verified.";
			String to = user.getEmail();
			Mail.generateAndSendEmail(subject, body, to);
		}
		else
		{
			model = new ModelAndView("DisplayMessage");
			model.addObject("msg", "Invalid verification code");
		}
		return model; 
	}

	@RequestMapping(value="/issued", method = RequestMethod.GET)
	public ModelAndView issuedBooks(HttpSession session) {
		ModelAndView model = null;
		
		User user = (User) session.getAttribute("user");
		
		if(user!=null && user.getUserType().equals("patron")){
			
			model = new ModelAndView("/User/IssuedBooks");
			model.addObject("user", user);
			
			issueDao = context.getBean(IssueDao.class);
			List<Book> issuedBooksList = issueDao.getIssuedBooks(user.getEmail());
			
			System.out.println("issuedBooksList length: " + issuedBooksList.size());
			
			if(issuedBooksList != null && issuedBooksList.size() >=1)
				model.addObject("issuedBooksList", issuedBooksList);
			else
				model.addObject("message", "You have Issued no books!");
		}else {
			model = new ModelAndView("error");
			model.addObject("error", "Login as a Patron to see your Issued Books!");
		}
		return model;
	}
}