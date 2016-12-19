package edu.sjsu.cmpe275.lab2.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
		String myquery = "select w from waitlist w where w.waitlistDate < '2020'";
		Query query = entitymanager.createQuery(myquery);
		List<Waitlist> list = (List<Waitlist>) query.getResultList();
		for(Waitlist waitlist : list)
		{
			System.out.println(waitlist.getWaitlistId());
		}
		/*String queryStr = "delete from Waitlist w where w.waitlistDate < '2017-10-10'";
		int i = entitymanager.createQuery(queryStr).executeUpdate();
		System.out.println("number of rows deleted" + i);*/
		
	}

	
}
