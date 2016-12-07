package edu.sjsu.cmpe275.lab2.logic;

import java.util.Calendar;
import java.util.Date;

public class DateService
{
	public static java.sql.Date addDate(int days)
	{
		Date date = new Date();
		
		Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        Date d1 = cal.getTime();
		
	
		java.sql.Date date2 = new java.sql.Date(d1.getTime());
		return date2;
	}
	
}
