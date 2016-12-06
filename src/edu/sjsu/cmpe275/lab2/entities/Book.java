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
	
	@Column(name = "number_of_copies")
	private int number_of_copies;
	
	@Column(name = "current_status")
	private String current_status;
	
	/*@Column(name = "keywords")
	private String keywords;
	*/
	@Column(name = "created_by")
	private String created_by;
	
	@Column(name = "updated_by")
	private String updated_by;
	/*@Column(name = "coverageimage")
	private Blob coverageimage;*/
}
