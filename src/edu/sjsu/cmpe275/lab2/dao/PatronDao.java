package edu.sjsu.cmpe275.lab2.dao;

import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.sjsu.cmpe275.lab2.entities.Patron;
import edu.sjsu.cmpe275.lab2.logic.Mail;

@Repository
public class PatronDao
{
	@PersistenceContext
	private EntityManager entitymanager;
	
	 @Transactional
	 public void createPatron(Patron patron)
	 {
		 entitymanager.merge(patron);
	 }

	 @Transactional
	public Patron getPatron(String email)
	{
		 Patron patron = entitymanager.find(Patron.class, email);
		 return patron;
	}
}
