package com.store.utils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

//@Component
public class EmailUtil {

	private final static String HOST = "localhost";
	public final static String SOURCE_EMAIL = "yaoxu@tubevpn.com";
		
	private static String SIGNUP_EMAIL_TEMPLATE = null;
	private static String FORGOT_PASSWORD_EMAIL_TEMPLATE = null;
	private static String CHANGE_PASSWORD_EMAIL_TEMPLATE = null;
	
//	@Resource("${email.flag}")
	private String bEmailServiceOn;

	public String getbEmailServiceOn() {
		return bEmailServiceOn;
	}

	public void setbEmailServiceOn(String bEmailServiceOn) {
		this.bEmailServiceOn = bEmailServiceOn;
	}

	static {
		// read sign up email template
		SIGNUP_EMAIL_TEMPLATE = READTEMPLATE("emails/signup.template");

		// read forgot password template
		FORGOT_PASSWORD_EMAIL_TEMPLATE = READTEMPLATE("emails/forgot.password.template");

		// read change password template
		CHANGE_PASSWORD_EMAIL_TEMPLATE = READTEMPLATE("emails/change.password.template");
	}

	public void sendSignUpEmail(String toEmail) {
		try {
			// no attachment, everything is on external link
			SendEmail(SOURCE_EMAIL, toEmail, "欢迎注册tubevpn",
					SIGNUP_EMAIL_TEMPLATE, null);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendForgotPasswordEmail(String toEmail, String changePasswordCode) {
		try {
			String email = FORGOT_PASSWORD_EMAIL_TEMPLATE.replaceAll("$code", changePasswordCode);
			// no attachment, everything is on external link
			SendEmail(SOURCE_EMAIL, toEmail, "tubevpn 忘记置密码服务",
					email, null);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendChangePasswordEmail(String toEmail) {
		try {
			// no attachment, everything is on external link
			SendEmail(SOURCE_EMAIL, toEmail, "tubevpn 修改密码服务",
					CHANGE_PASSWORD_EMAIL_TEMPLATE, null);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void SendEmail(String from, String to, String header,
			String content, String attachmentPath) throws AddressException,
			MessagingException {
		
		if(!"true".equalsIgnoreCase(bEmailServiceOn)) {
			return;
		}

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.setProperty("mail.smtp.host", HOST);

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);

		// Create a default MimeMessage object.
		MimeMessage message = new MimeMessage(session);

		// Set From: header field of the header.
		message.setFrom(new InternetAddress(from));

		// Set To: header field of the header.
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

		// Set Subject: header field
		message.setSubject(header);

		// Create the message part
		BodyPart messageBodyPart = new MimeBodyPart();

		// Fill the message
		messageBodyPart.setText(content);

		// Create a multipar message
		Multipart multipart = new MimeMultipart();

		// Set text message part
		multipart.addBodyPart(messageBodyPart);

		// Part two is attachment
		messageBodyPart = new MimeBodyPart();
		DataSource source = new FileDataSource(attachmentPath);

		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(attachmentPath);
		multipart.addBodyPart(messageBodyPart);

		// Send the complete message parts
		message.setContent(multipart);

		// Send message
		Transport.send(message);
	}

	private static String READTEMPLATE(String templateRelativePath) {
		ClassLoader classLoader = EmailUtil.class.getClassLoader();

		// read sign up email template
		File file = new File(classLoader.getResource(templateRelativePath)
				.getFile());
		StringBuilder signup_email_template = new StringBuilder("");
		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				signup_email_template.append(line).append("\n");
			}
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return signup_email_template.toString();
	}

	public static void main(String[] args) {
		EmailUtil util = new EmailUtil();
		util.sendSignUpEmail("semuelxu@gmail.com");
	}
}
