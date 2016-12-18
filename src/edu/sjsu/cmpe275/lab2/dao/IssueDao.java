package edu.sjsu.cmpe275.lab2.dao;

import java.sql.SQLException;
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
import edu.sjsu.cmpe275.lab2.entities.Waitlist;

@Repository
public class IssueDao
{
	@PersistenceContext
	private EntityManager entitymanager;
	
	@Transactional
	 public void checkout(ArrayList<Issue> issueList, ArrayList<Book> bookList, User user, ArrayList<Waitlist> waitlists) 
	 {
		for(Issue issue:issueList)
		{
			 entitymanager.persist(issue);
		}
		
		for(Book book: bookList)
		{
			entitymanager.merge(book);
			String queryStr = "delete from Waitlist w where w.bookId=" + book.getBookid() + " and w.userEmail='" + user.getEmail() + "'";
			int i = entitymanager.createQuery(queryStr).executeUpdate();
		}
		
		for(Waitlist waitlist : waitlists)
		{
			entitymanager.persist(waitlist);
			
		}
		
		
		entitymanager.merge(user);
		
	 }
	
	@Transactional
	public int countQuery(String query1)
	 {
		 int count = Integer.parseInt(entitymanager.createQuery(query1).getSingleResult().toString());
	      return count;
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
	
	@Transactional
	public Issue getIssueById(String queryId){
		try {
			Query query = entitymanager.createQuery(queryId);
			Issue issue = (Issue)query.getSingleResult();
			return issue;
		} catch(NoResultException e){
			System.out.println("No Issue found for user!");
			return null;
		}
	}
	
	@Transactional
	public List<Issue> getIssuesById(String queryId){
		try {
			Query query = entitymanager.createQuery(queryId);
			List<Issue> issueList = (List<Issue>)query.getResultList();
			return issueList;
		} catch(NoResultException e){
			System.out.println("No Issue found for user!");
			return null;
		}
	}
	
	@Transactional
	public List<Integer> getBookIds(String queryId){
		try {
			Query query = entitymanager.createQuery(queryId);
			List<Integer> bookIds =  query.getResultList();
			
			for(Integer temp: bookIds)
			{
				System.out.println(temp);
			}
			return bookIds;
			
		} catch(NoResultException e){
			return null;
		}
	}
	
	@Transactional
	public void deleteIssue(int issueid)
	{
		Issue issue= entitymanager.find(Issue.class, issueid);
		entitymanager.remove(issue);
	}
	
	@Transactional
	public int deleteIssues(String queryDelete) {
		
		try {
			int rowsDeleted = entitymanager.createQuery(queryDelete).executeUpdate();
			return rowsDeleted;
			
		} catch(NoResultException e){
			e.printStackTrace();
			return 0;
		}
	}
	
	@Transactional
	public void updateIssue(Issue issue){
		entitymanager.merge(issue);
	}
}
