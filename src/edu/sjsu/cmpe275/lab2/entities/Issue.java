package edu.sjsu.cmpe275.lab2.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "issue")
public class Issue
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "issueId")
	private int issueId;
	
	@Column(name = "userEmail")
	private String userEmail;
	
	@Column(name = "bookId")
	private int bookId;
	
	@Column(name = "issueDate")
	private Date issueDate;
	
	@Column(name = "dueDate")
	private Date dueDate;
	
	@Column(name = "renewalCount")
	private int renewalCount;

	public int getIssueId()
	{
		return issueId;
	}

	public void setIssueId(int issueId)
	{
		this.issueId = issueId;
	}

	public String getUserEmail()
	{
		return userEmail;
	}

	public void setUserEmail(String userEmail)
	{
		this.userEmail = userEmail;
	}

	public int getBookId()
	{
		return bookId;
	}

	public void setBookId(int bookId)
	{
		this.bookId = bookId;
	}

	public Date getIssueDate()
	{
		return issueDate;
	}

	public void setIssueDate(Date issueDate)
	{
		this.issueDate = issueDate;
	}

	public Date getDueDate()
	{
		return dueDate;
	}

	public void setDueDate(Date dueDate)
	{
		this.dueDate = dueDate;
	}

	public int getRenewalCount()
	{
		return renewalCount;
	}

	public void setRenewalCount(int renewalCount)
	{
		this.renewalCount = renewalCount;
	}
	
}
