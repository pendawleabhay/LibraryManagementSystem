package edu.sjsu.cmpe275.lab2.controllers;

import java.util.Map;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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

import edu.sjsu.cmpe275.lab2.dao.PatronDao;
import edu.sjsu.cmpe275.lab2.logic.Mail;
import edu.sjsu.cmpe275.lab2.entities.Patron;

@Controller
@RequestMapping(value = "/user")
public class UserController 
{
	
	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
	
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
	public ModelAndView signIn(@RequestParam("email") String email, @RequestParam("password") String password)
	{
		PatronDao dao = context.getBean(PatronDao.class);
		Patron patron = dao.getPatron(email);
		ModelAndView model;
		if(patron.getPassword().equals(password) && patron.getIsVerified()==1)
		{
			/*model = new ModelAndView("DisplayMessage");
			model.addObject("msg", "login successfull");*/
			model = new ModelAndView("User/Homepage");
		}
		else
		{
			model = new ModelAndView("DisplayMessage");
			model.addObject("msg", "unsuccessfull login");
		}
		return model; 
	}
	
	@RequestMapping(value = "/signUp", method = RequestMethod.POST)
	public ModelAndView signUp(@ModelAttribute("patron")  Patron patron)
	{
		PatronDao dao = context.getBean(PatronDao.class);
		ModelAndView model;
		
		// check if sjsu id exists
		int count = dao.countQuery("select count(p) from Patron p where p.sjsuId=" + patron.getSjsuId());
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
			patron.setVerificationCode(code);
			
			// set user type 
			if(patron.getEmail().contains("@sjsu.edu"))
			{
				patron.setUserType("librarian");
			}
			else
			{
				patron.setUserType("patron");
			}
			
			// save user
			dao.createPatron(patron);
			
			// send verification mail
			String subject = "Verify Your Library Account";
			String body = "Your library account verfication code is following.<br><br><h1>" + code + "</h1>";
			String to = patron.getEmail();
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
		PatronDao dao = context.getBean(PatronDao.class);
		Patron patron = dao.getPatron(email);
		ModelAndView model;
		if(patron.getVerificationCode() == code)
		{
			patron.setIsVerified(1);
			dao.createPatron(patron);
			model = new ModelAndView("User/Homepage");
			
			// send confirmation mail
			String subject = "Library Account Verified";
			String body = "Your library account is now verified.";
			String to = patron.getEmail();
			Mail.generateAndSendEmail(subject, body, to);
		}
		else
		{
			model = new ModelAndView("DisplayMessage");
			model.addObject("msg", "Invalid verification code");
		}
		return model; 
	}
}