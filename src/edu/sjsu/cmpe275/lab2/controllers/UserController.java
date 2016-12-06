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
	
	@RequestMapping(value = "/signIn", method = RequestMethod.POST)
	public ModelAndView signIn(@RequestParam("email") String email, @RequestParam("password") String password)
	{
		PatronDao dao = context.getBean(PatronDao.class);
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
		}
		return model; 
	}
	
	@RequestMapping(value="/getSignUp")
	public ModelAndView userForm()
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
	
	@RequestMapping(value = "/verify", method = RequestMethod.POST)
	public ModelAndView signUp(@RequestParam("email") String email, @RequestParam("code") int code)
	{
		PatronDao dao = context.getBean(PatronDao.class);
		Patron patron = dao.getPatron(email);
		ModelAndView model;
		if(patron.getVerificationCode() == code)
		{
			patron.setIsVerified(1);
			dao.createPatron(patron);
			model = new ModelAndView("DisplayMessage");
			model.addObject("msg", "Your account is now verified");
		}
		else
		{
			model = new ModelAndView("DisplayMessage");
			model.addObject("msg", "Invalid verification code");
		}
		return model; 
	}
	
	@RequestMapping(value = "/signUp", method = RequestMethod.POST)
	public ModelAndView signUp(@ModelAttribute("patron")  Patron patron)
	{
		// generate verification code
		Random rand = new Random();
		int  code = rand.nextInt(999999) + 100000;
		patron.setVerificationCode(code);
		
		// save patron
		PatronDao dao = context.getBean(PatronDao.class);
		dao.createPatron(patron);
		
		// send verification mail
		String subject = "Verify Your Library Account";
		String body = "Your library account verfication code is following.<br><br><h1>" + code + "</h1>";
		String to = patron.getEmail();
		Mail.generateAndSendEmail(subject, body, to);
		
		
		ModelAndView model = new ModelAndView("DisplayMessage");
		model.addObject("msg", "sign up successfull");
		return model; 
	}
	
	/*@RequestMapping(value="")
	public ModelAndView userForm()
	{
		
		ModelAndView model = new ModelAndView("User/UserForm");
		return model;
	}

	@RequestMapping(value = "/userId"  , method = RequestMethod.POST)
	public ModelAndView createUser(  @RequestParam Map<String, String> queryMap)
	{
			
		UserDao dao = context.getBean(UserDao.class);
		User user = dao.createUser(queryMap);
		ModelAndView model = new ModelAndView("User/UserDetail"); 
		model.addObject(user);
		return model;
	}
	
	
	
	@RequestMapping(value = "/{userId}")
	public Object getUpdateDeleteUser(@RequestParam Map<String, String> queryMap, @PathVariable("userId") int userId, @RequestParam(required = false, defaultValue = "false" ) boolean json)
	{
		UserDao dao = context.getBean(UserDao.class);
		User user;
		
		if(queryMap.isEmpty())
		{
			
			user = dao.getUser(userId);
		}
		else if(json)
		{
			return new ResponseEntity<User>(dao.getUser(userId), HttpStatus.OK);
		}
		else if(queryMap.get("method").equals("update"))
		{
			user = dao.updateUser(queryMap,userId);
		}
		else
		{		
			dao.deleteUser(userId);
			ModelAndView model = new ModelAndView("/User/UserForm"); 
			return model;
		}
		ModelAndView model = new ModelAndView("User/UserDetail"); 
		model.addObject(user);
		return model;
	}

	public ResponseEntity<User> getUser(int id) {
		UserDao dao = context.getBean(UserDao.class);
		User user = dao.getUser(id);
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}
	*/
	
	
}