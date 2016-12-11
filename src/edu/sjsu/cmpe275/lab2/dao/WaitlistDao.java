package edu.sjsu.cmpe275.lab2.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.sjsu.cmpe275.lab2.entities.Issue;
import edu.sjsu.cmpe275.lab2.entities.Waitlist;

@Repository
public class WaitlistDao
{

	@PersistenceContext
	private EntityManager entitymanager;
	
	@Transactional
	public String getHighestWaitlist(int bookId)
	{
		String myquery = "select w from Waitlist w where w.bookId = " + bookId + " order by w.waitlistDate ";
		Query query = entitymanager.createQuery(myquery).setMaxResults(1);
		Waitlist waitlist =  (Waitlist) query.getSingleResult();
		return waitlist.getUserEmail() ;
	}
}
