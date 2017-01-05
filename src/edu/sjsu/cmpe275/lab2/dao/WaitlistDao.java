package edu.sjsu.cmpe275.lab2.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.google.api.client.util.DateTime;

import edu.sjsu.cmpe275.lab2.entities.Issue;
import edu.sjsu.cmpe275.lab2.entities.User;
import edu.sjsu.cmpe275.lab2.entities.Waitlist;
import edu.sjsu.cmpe275.lab2.logic.DateService;

@Repository
public class WaitlistDao
{

	@PersistenceContext
	private EntityManager entitymanager;
	
	@Transactional
	public List<Waitlist> getHighestWaitlist(int bookId, int limit)
	{
		String myquery = "select w from Waitlist w where w.bookId = " + bookId + " order by w.waitlistDate ";
		Query query = entitymanager.createQuery(myquery).setMaxResults(limit);
		List<Waitlist> list = (List<Waitlist>)query.getResultList();
		return list;
	}
	
	@Transactional
	public List<Waitlist> getWaitlistByUserEmail(String email)
	{
		String myquery = "select w from Waitlist w where w.userEmail = '" + email + "'";
		Query query = entitymanager.createQuery(myquery);
		List<Waitlist> list = (List<Waitlist>)query.getResultList();
		return list;
	}

	@Transactional
	public void removeReserved(Date date)
	{
		    Calendar cal = Calendar.getInstance();
		    cal.setTime(date);
		    int year = cal.get(Calendar.YEAR);
		    int month = cal.get(Calendar.MONTH);
		    int day = cal.get(Calendar.DAY_OF_MONTH); 
		String queryStr = "delete from Waitlist w where w.waitlistDate < '" + year + "-" + day + "-" + month +  "'";
		int i = entitymanager.createQuery(queryStr).executeUpdate();
	}

	
}
