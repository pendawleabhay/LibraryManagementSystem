package edu.sjsu.cmpe275.lab2.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.sjsu.cmpe275.lab2.entities.Book;


@Repository
public class BookDao {
	
	@PersistenceContext
	private EntityManager entitymanager;
	
	@Transactional
	public void createBook(Book book) {
		 entitymanager.persist(book);
	}
	
	@Transactional
	public void updateBook(Book book) {
		System.out.println(book.toString());
		 entitymanager.merge(book);
	}
	
	//Get Books by Different Search Type
	@Transactional
	public List<Book> getBookBySearchType(String querySearch){
		Query query = entitymanager.createQuery(querySearch);
		try{
			List<Book> bookList = (List<Book>)query.getResultList();
			System.out.println("bookList Length: " + bookList.size());
			return bookList;
		} catch(NoResultException e){
			System.out.println("No Books Found!");
			return null;
		}
	}
	
	//Get Book By ID
	@Transactional
	public Book getBookById(int bookid){
		Book book = entitymanager.find(Book.class, bookid);
		return book;
	}
	
	//Get Book By Title
	@Transactional
	public Book getBookByTitle(String queryByTitle){
		Query query = entitymanager.createQuery(queryByTitle);
		try{
			Book book = (Book)query.getSingleResult();
			System.out.println("book: " + book);
			return book;
		} catch(NoResultException e){
			System.out.println("No Book Found!");
			return null;
		}
	}
	
	//Get Books by Keyword
	@Transactional
	public List<Book> getBooksByKeyword(String queryByKeyword) {
		Query query = entitymanager.createQuery(queryByKeyword);
		List<Book> bookList = (List<Book>)query.getResultList();
		return bookList;
	}
	
	//Get All Books
	@Transactional
	public List<Book> getAllBooks(String queryAllBooks) {
		Query query = entitymanager.createQuery(queryAllBooks);
		List<Book> bookList = (List<Book>)query.getResultList();
		return bookList;
	}
	
	@Transactional
	public void deleteBook(int bookid){
		Book book = entitymanager.find(Book.class, bookid);
		entitymanager.remove(book);
	}

	@Transactional
	public void reserveBook(Book book)
	{
		entitymanager.merge(book);
		//Query query = entitymanager.createQuery("delete from Waitlist w where w.bookId=" + book.getBookid());
	}
	
	@Transactional
	public int updateBooks(String queryUpdate) {
		try {
			int rowsUpdated = entitymanager.createQuery(queryUpdate).executeUpdate();
			return rowsUpdated;
			
		} catch(NoResultException e){
			e.printStackTrace();
			return 0;
		}
	}
}