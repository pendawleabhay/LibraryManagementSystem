package edu.sjsu.cmpe275.lab2.controllers;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class frontcontroller
{
	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
	
	@RequestMapping(value="/")
	public ModelAndView root()
	{
		
		ModelAndView model = new ModelAndView("index");
		return model;
	}
	
}
