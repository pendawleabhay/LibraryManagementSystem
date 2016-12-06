package edu.sjsu.cmpe275.lab2.dao;

import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.sjsu.cmpe275.lab2.entities.User;
import edu.sjsu.cmpe275.lab2.logic.Mail;

@Repository
public class UserDao
{
	@PersistenceContext
	private EntityManager entitymanager;
	
	 @Transactional
	 public void createUser(User user)
	 {
		 entitymanager.persist(user);
	 }
	 
	 @Transactional
	 public void updateUser(User user)
	 {
		 entitymanager.merge(user);
	 }

	 @Transactional
	public User getUser(String email)
	{
		 User user = entitymanager.find(User.class, email);
		 return user;
	}
	 
	 @Transactional
	 public List<User> executeQuery(String query1)
	 {
		 Query query = entitymanager.createQuery(query1);
	      List<User> list = (List<User>)query.getResultList();
	      return list;
	 }
	 
	 @Transactional
	 public int countQuery(String query1)
	 {
		/* Query query = entitymanager.createQuery(query1);
	      int count = (Integer)query.getSingleResult();*/
		 int count = Integer.parseInt(entitymanager.createQuery(query1).getSingleResult().toString());
	      return count;
	 }
}
