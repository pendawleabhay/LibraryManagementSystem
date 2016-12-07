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
	public List<Book> getIssuedBooks(String userEmail) {
		List<Book> issuedBooks = new ArrayList<Book>();
		String queryGetIssuedBookId = "select i.bookId from Issue i where i.userEmail='" + userEmail + "'";
		
		Query queryForIssuedBookId = entitymanager.createQuery(queryGetIssuedBookId);
		
		try{
			List<Integer> issuedBookidList = (List<Integer>)queryForIssuedBookId.getResultList();
			System.out.println("issuedBookList length: " + issuedBookidList.size());
			
			if(issuedBookidList.size()>=1){
				for(int index = 0;index < issuedBookidList.size(); index++){
					String queryGetIssuedBooks = "select b from Book b where b.bookid = " + issuedBookidList.get(index);
					Query query = entitymanager.createQuery(queryGetIssuedBooks);
					Book book = (Book)query.getSingleResult();
					issuedBooks.add(book);
				}
			}
			//issuedBooks = (List<Book>)query.getResultList();
			System.out.println("List length is: " + issuedBooks.size());
		} catch(NoResultException e) {
			System.out.println("No books found for user " + userEmail);
			return null;
		}
		return issuedBooks;
	}
}
