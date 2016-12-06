package edu.sjsu.cmpe275.lab2.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "patron")
public class Patron {
	
		@Id
		@Column(name = "email")
		private String email;
		
		@Column(name = "sjsuId")
		private int sjsuId;
		
		@Column(name = "password")
		private String password;
		
		@Column(name = "name")
		private String name;
		  
		@Column(name = "isVerified")
		private int isVerified;
		
		@Column(name = "noOfBooksIssued")
		private int noOfBooksIssued;
		
		@Column(name = "verificationCode")
		private int verificationCode;

		public int getVerificationCode()
		{
			return verificationCode;
		}

		public void setVerificationCode(int verificationCode)
		{
			this.verificationCode = verificationCode;
		}

		public int getSjsuId()
		{
			return sjsuId;
		}

		public void setSjsuId(int sjsuId)
		{
			this.sjsuId = sjsuId;
		}

		public String getEmail()
		{
			return email;
		}

		public void setEmail(String email)
		{
			this.email = email;
		}

		public String getPassword()
		{
			return password;
		}

		public void setPassword(String password)
		{
			this.password = password;
		}

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}

		public int getIsVerified()
		{
			return isVerified;
		}

		public void setIsVerified(int isVerified)
		{
			this.isVerified = isVerified;
		}

		public int getNoOfBooksIssued()
		{
			return noOfBooksIssued;
		}

		public void setNoOfBooksIssued(int noOfBooksIssued)
		{
			this.noOfBooksIssued = noOfBooksIssued;
		}
		
		
}