package edu.sjsu.cmpe275.lab2.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.sjsu.cmpe275.lab2.entities.User;

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
}
