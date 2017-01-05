package edu.sjsu.cmpe275.lab2.controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpSession;

import org.eclipse.persistence.jpa.config.DataService;
import org.eclipse.persistence.jpa.config.PersistenceUnit;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.sjsu.cmpe275.lab2.entities.User;
import edu.sjsu.cmpe275.lab2.logic.DateService;

@Controller
public class frontcontroller
{
	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
	@RequestMapping(value="/")
	public ModelAndView root(HttpSession session)
	{	
		User user = (User)session.getAttribute("user");
		ModelAndView model;
		if(user!=null){
			if(user.getUserType().equals("librarian"))
				model = new ModelAndView("LibrarianHomepage");
			else
				model = new ModelAndView("PatronHomepage");
		} else {
			model = new ModelAndView("index");
		}
		return model;
	}
	
	
	//Setting the date as per requirement
	@RequestMapping(value="/setDate", method = RequestMethod.POST)
	public ModelAndView setDate(@RequestParam("date") String str)
	{
		ModelAndView model;
		String year = str.substring(0,4);
		String month = str.substring(5, 7);
		String date = str.substring(8, 10);

		Date date1 = DateService.getInstance().stringToDate(year, month, date);
		if(date1.compareTo(DateService.getInstance().getDate())<0)
		{
			model = new ModelAndView("/User/LibrarianHomepage");
			model.addObject("message1","You cannot set past date");
		}
		else
		{
			DateService.getInstance().setDate(date1);
			model = new ModelAndView("/User/LibrarianHomepage");
			model.addObject("message1","Email sent to all patrons");
		}
		
		return model;
	}
	
}
