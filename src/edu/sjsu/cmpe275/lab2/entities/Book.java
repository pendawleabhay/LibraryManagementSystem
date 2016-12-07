package edu.sjsu.cmpe275.lab2.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "book")
public class Book {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "bookid")
	private int bookid;
	
	@Column(name = "author")
	private String author;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "call_number")
	private String call_number;
	
	@Column(name = "publisher")
	private String publisher;
	
	@Column(name = "year_of_publication")
	private int year_of_publication;
	
	@Column(name = "location_in_library")
	private String location_in_library;
	
	@Column(name = "copies_available")
	private int copies_available;
	
	@Column(name = "number_of_copies")
	private int number_of_copies;
	
	@Column(name = "current_status")
	private int current_status;
	
	@Column(name = "keywords")
	private String keywords;
	
	@Column(name = "created_by")
	private String created_by;
	
	@Column(name = "updated_by")
	private String updated_by;
	/*@Column(name = "coverageimage")
	private Blob coverageimage;*/

	public int getBookid() {
		return bookid;
	}

	public void setBookid(int bookid) {
		this.bookid = bookid;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCall_number() {
		return call_number;
	}

	public void setCall_number(String call_number) {
		this.call_number = call_number;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getYear_of_publication() {
		return year_of_publication;
	}

	public void setYear_of_publication(int year_of_publication) {
		this.year_of_publication = year_of_publication;
	}

	public String getLocation_in_library() {
		return location_in_library;
	}

	public void setLocation_in_library(String location_in_library) {
		this.location_in_library = location_in_library;
	}

	public int getCopies_available() {
		return copies_available;
	}

	public void setCopies_available(int copies_available) {
		this.copies_available = copies_available;
	}

	public int getNumber_of_copies() {
		return number_of_copies;
	}

	public void setNumber_of_copies(int number_of_copies) {
		this.number_of_copies = number_of_copies;
	}

	public int getCurrent_status() {
		return current_status;
	}

	public void setCurrent_status(int current_status) {
		this.current_status = current_status;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public String getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}
}
