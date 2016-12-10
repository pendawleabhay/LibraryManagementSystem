package edu.sjsu.cmpe275.lab2.entities;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "waitlist")
public class Waitlist
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "waitlistId")
	private int waitlistId;
	
	@Column(name = "userEmail")
	private String userEmail;
	
	
	@Column(name = "bookId")
	private int bookId;
	
	@Column(name = "waitlistDate")
	private Date waitlistDate;
	
	

	public String getUserEmail()
	{
		return userEmail;
	}

	public void setUserEmail(String userEmail)
	{
		this.userEmail = userEmail;
	}

	public int getWaitlistId()
	{
		return waitlistId;
	}

	public void setWaitlistId(int waitlistId)
	{
		this.waitlistId = waitlistId;
	}


	public int getBookId()
	{
		return bookId;
	}

	public void setBookId(int bookId)
	{
		this.bookId = bookId;
	}

	public Date getWaitlistDate()
	{
		return waitlistDate;
	}

	public void setWaitlistDate(Date waitlistDate)
	{
		this.waitlistDate = waitlistDate;
	}
}
