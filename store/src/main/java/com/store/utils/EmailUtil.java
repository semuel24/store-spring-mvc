package com.store.utils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

/**
 * bean initialized in applicationContext.xml
 */
public class EmailUtil {

	private final static String HOST = "localhost";
	public final static String SOURCE_EMAIL = "contact@tubevpn.com";
	
	//email template path
	private String signupEmailTemplatePath = null;
	private String forgotPasswordEmailTemplatePath = null;
	private String changePasswordEmailTemplatePath = null;
	
	//email template content
	private String signupEmailTemplate = null;
	private String forgotPasswordEmailTemplate = null;
	private String changePasswordEmailTemplate = null;
	
	
	private String bEmailServiceOn;

	public String getbEmailServiceOn() {
		return bEmailServiceOn;
	}

	public void setbEmailServiceOn(String bEmailServiceOn) {
		this.bEmailServiceOn = bEmailServiceOn;
	}

	public String getSignupEmailTemplatePath() {
		return signupEmailTemplatePath;
	}

	public void setSignupEmailTemplatePath(String signupEmailTemplatePath) {
		this.signupEmailTemplatePath = signupEmailTemplatePath;
	}

	public String getForgotPasswordEmailTemplatePath() {
		return forgotPasswordEmailTemplatePath;
	}

	public void setForgotPasswordEmailTemplatePath(
			String forgotPasswordEmailTemplatePath) {
		this.forgotPasswordEmailTemplatePath = forgotPasswordEmailTemplatePath;
	}

	public String getChangePasswordEmailTemplatePath() {
		return changePasswordEmailTemplatePath;
	}

	public void setChangePasswordEmailTemplatePath(
			String changePasswordEmailTemplatePath) {
		this.changePasswordEmailTemplatePath = changePasswordEmailTemplatePath;
	}

	public String getSignupEmailTemplate() throws IOException {
		if(signupEmailTemplate != null) {
			return signupEmailTemplate;
		}
		signupEmailTemplate = readTemplate(getSignupEmailTemplatePath());
		return signupEmailTemplate;
	}

	public String getForgotPasswordEmailTemplate() throws IOException {
		if(forgotPasswordEmailTemplate != null) {
			return forgotPasswordEmailTemplate;
		}
		forgotPasswordEmailTemplate = readTemplate(getForgotPasswordEmailTemplatePath());
		return forgotPasswordEmailTemplate;
	}

	public String getChangePasswordEmailTemplate() throws IOException {
		if(changePasswordEmailTemplate != null) {
			return changePasswordEmailTemplate;
		}
		changePasswordEmailTemplate = readTemplate(getChangePasswordEmailTemplatePath());
		return changePasswordEmailTemplate;
	}

	public void sendSignUpEmail(String toEmail) {
		try {
			// no attachment, everything is on external link
			SendEmail(SOURCE_EMAIL, toEmail, "欢迎注册tubevpn",
					getSignupEmailTemplate(), null);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendForgotPasswordEmail(String toEmail, String changePasswordCode) {
		try {
			String email = getForgotPasswordEmailTemplate().replaceAll("$code", changePasswordCode);
			// no attachment, everything is on external link
			SendEmail(SOURCE_EMAIL, toEmail, "tubevpn 忘记置密码服务",
					email, null);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendChangePasswordEmail(String toEmail) {
		try {
			// no attachment, everything is on external link
			SendEmail(SOURCE_EMAIL, toEmail, "tubevpn 修改密码服务",
					getChangePasswordEmailTemplate(), null);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
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
		if(attachmentPath != null) {
			messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(attachmentPath);

			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(attachmentPath);
			multipart.addBodyPart(messageBodyPart);
		}

		// Send the complete message parts
		message.setContent(multipart);

		// Send message
		Transport.send(message);
	}

//	private String readTemplate(String templateAbsolutePath) {
//
//		// read sign up email template
//		File file = new File(templateAbsolutePath);
//		StringBuilder signup_email_template = new StringBuilder("");
//		try (Scanner scanner = new Scanner(file)) {
//			while (scanner.hasNextLine()) {
//				String line = scanner.nextLine();
//				signup_email_template.append(line).append("\n");
//			}
//			scanner.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return signup_email_template.toString();
//	}
	
	private String readTemplate(String templateAbsolutePath) throws IOException {

		return Files.toString(new File(templateAbsolutePath), Charsets.UTF_8);
	}

	public static void main(String[] args) throws IOException {
		EmailUtil util = new EmailUtil();
		String content = util.readTemplate("/Users/yaoxu/fuhu/code/play/store-spring-mvc/store/tomcat/conf/emails/signup.template");
		System.out.println(content);
	}
}
