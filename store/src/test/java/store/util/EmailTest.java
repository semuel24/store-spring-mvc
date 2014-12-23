package store.util;

import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

@Ignore
//http://www.tutorialspoint.com/java/java_sending_email.htm
public class EmailTest {

	@Test
	public void testSendEmail() {
		sendHtmlEmail();
	}

	private void sendHtmlEmail() {
		// Recipient's email ID needs to be mentioned.
		String to = "to@gmail.com";

		// Sender's email ID needs to be mentioned
		String from = "from@gmail.com";

		// Assuming you are sending email from localhost
		String host = "localhost";

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.setProperty("mail.smtp.host", host);

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					to));

			// Set Subject: header field
			message.setSubject("This is the Subject Line!");

			// Send the actual HTML message, as big as you like
			message.setContent("<h1>This is actual message</h1>", "text/html");

			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
