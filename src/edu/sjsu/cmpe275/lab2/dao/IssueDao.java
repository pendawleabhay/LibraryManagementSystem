package edu.sjsu.cmpe275.lab2.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.sjsu.cmpe275.lab2.entities.Book;
import edu.sjsu.cmpe275.lab2.entities.Issue;
import edu.sjsu.cmpe275.lab2.entities.User;

@Repository
public class IssueDao
{
	@PersistenceContext
	private EntityManager entitymanager;
	
	@Transactional
	 public void checkout(ArrayList<Issue> issueList, ArrayList<Book> bookList, User user)
	 {
		for(Issue issue:issueList)
		{
			 entitymanager.persist(issue);
		}
		
		for(Book book: bookList)
		{
			entitymanager.merge(book);
		}
		
		entitymanager.merge(user);
		
	 }
	
	
}
