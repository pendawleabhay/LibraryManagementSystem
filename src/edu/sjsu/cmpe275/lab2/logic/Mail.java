package edu.sjsu.cmpe275.lab2.logic;
 
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class Mail {
 
	static Properties mailServerProperties;
	static Session getMailSession;
	static MimeMessage generateMailMessage;
 
	public static void generateAndSendEmail(String subject, String emailBody, String to) {
			// Step1
			System.out.println("\n 1st ===> setup Mail Server Properties..");
			mailServerProperties = System.getProperties();
			mailServerProperties.put("mail.smtp.port", "587");
			mailServerProperties.put("mail.smtp.auth", "true");
			mailServerProperties.put("mail.smtp.starttls.enable", "true");
			System.out.println("Mail Server Properties have been setup successfully..");
	 
			// Step2
			System.out.println("\n\n 2nd ===> get Mail Session..");
			getMailSession = Session.getDefaultInstance(mailServerProperties, null);
			generateMailMessage = new MimeMessage(getMailSession);
			try
			{
				generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
				//generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress("test2@gmail.com"));
				generateMailMessage.setSubject(subject);
				generateMailMessage.setContent(emailBody, "text/html");
			} catch (AddressException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Mail Session has been created successfully..");
	 
			// Step3
			System.out.println("\n\n 3rd ===> Get Session and Send mail");
			Transport transport;	 
			try
			{
				transport = getMailSession.getTransport("smtp");
				transport.connect("smtp.gmail.com", "librarymanagementsys@gmail.com", "sjsucmpe275");
				transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
				transport.close();
				System.out.println("\n\n ===> Your Java Program has just sent an Email successfully. Check your email..");
			} catch (MessagingException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		
	}
}