package edu.sjsu.cmpe275.lab2.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.sjsu.cmpe275.lab2.entities.Issue;
import edu.sjsu.cmpe275.lab2.entities.User;
import edu.sjsu.cmpe275.lab2.entities.Waitlist;

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
	public List<Waitlist> getWaitlistByUserEmail()
	{
		return null;
		
	}
}
