package edu.sjsu.cmpe275.lab2.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
	
	@Transactional
	public List<Issue> getIssuedBooksId(String userEmail) {
		List<Book> issuedBooks = new ArrayList<Book>();
		String queryGetIssuedBookId = "select i from Issue i where i.userEmail='" + userEmail + "'";
		
		Query queryForIssuedBookId = entitymanager.createQuery(queryGetIssuedBookId);
		
		try{
			List<Issue> issuedBookidList = (List<Issue>)queryForIssuedBookId.getResultList();
			System.out.println("issuedBookList length: " + issuedBookidList.size());
			
			if(issuedBookidList.size()>=1)
				return issuedBookidList;
			else
				return null;
		} catch(NoResultException e) {
			System.out.println("No books found for user " + userEmail);
			return null;
		}
	}
	
	@Transactional
	public List<Book> getIssuedBooks(List<Issue> issuedBookidList) {
		List<Book> issuedBooks;
		System.out.println("in getIssuedBooks Dao");
		System.out.println("getIssuedBookidList size: " + issuedBookidList.size());
		try{
			
			if(issuedBookidList.size()>=1){
				issuedBooks = new ArrayList<Book>();
				for(int index = 0;index < issuedBookidList.size(); index++){
					Issue issue = issuedBookidList.get(index);
					String queryGetIssuedBooks = "select b from Book b where b.bookid = " + issue.getBookId();
					Query query = entitymanager.createQuery(queryGetIssuedBooks);
					Book book = (Book)query.getSingleResult();
					issuedBooks.add(book);
				}
				System.out.println("List length in IssdueDao is: " + issuedBooks.size());
				return issuedBooks;
			} else 
				return null;
			//issuedBooks = (List<Book>)query.getResultList();
			
		} catch(NoResultException e) {
			System.out.println("No books found for user!");
			return null;
		}
	}
}
