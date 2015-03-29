package com.store.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.store.result.CreateUserResult;
import com.store.result.HandleForgotPasswordResult;
import com.store.result.LoginResult;
import com.store.result.StatusResult;
import com.store.service.UserService;
import com.store.utils.Constants;
import com.store.utils.EmailUtil;
import com.store.web.form.ContactForm;
import com.store.web.form.ForgotpasswordForm;
import com.store.web.form.LoginForm;
import com.store.web.form.SignUpForm;

@Controller
public class UserController {
	
	private static final Logger logger = LoggerFactory
			.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private Validator validator;
	
	@Autowired
	private EmailUtil emailUtil;

	/**
	 * steps:
	 * 1. validate inputs format
	 * 2. validate email not exist
	 * 3.1 create user in DB, bind default free-trial product to user and usage to 0
	 * 3.2 put user data to redis through API service
	 * 4. put user data into tomcat session object
	 * 6. (async) send confirmation e-mails
	 */
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String handleSignUp(
			@ModelAttribute("signupForm") SignUpForm signupForm,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// clear app server session
		request.getSession().invalidate();

		// (optional)validate input format
//		Set<ConstraintViolation<SignUpForm>> constraintViolations = validator
//				.validate(signupForm);
//		if (constraintViolations.size() > 0) {
//			return "/client/signup";// should not hit here
//		}
//		if(signupForm.getAgree() == null || signupForm.getAgree().length == 0) {
//			return "/client/signup";// should not hit here
//		}

		// (optional)validate if username and email already exists in database

		// create a new user in database
		CreateUserResult result = null;
		try{
			result = userService.createUser(signupForm);
		} catch(Exception ex) {
			if(logger.isErrorEnabled()) {
				logger.error(ex.getMessage(), ex);
			}
		}
		if(result == null) {
			model.put("message", StatusResult
					.convertErrorCode2Message(Constants.GENERAL_FAILURE));
			return "/client/message";
		}
		if (!Constants.SUCCESS.equalsIgnoreCase(result.getStatus())) {
			model.put("message", result.getMessage());
			return "/client/message";
		}

		// populate app server session
		request.getSession().setMaxInactiveInterval(
				Constants.DEFAULT_SESSION_TIMEOUT);
		request.getSession().setAttribute(Constants.SESSION,
				result.getSessionkey());

		// *send email notification with attached profile file(async)
		emailUtil.sendSignUpEmail(signupForm.getEmail());

		// return necessary message to web page according to error code
		model.put("message", result.getMessage());
		model.put("user", signupForm);
		return "/client/message";
	}	

	@RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
	public String handleForgotPassard(
			@ModelAttribute("forgotpasswordForm") ForgotpasswordForm forgotpasswordForm,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {

		// (Optional)validate email format

		// change to a machine-generated password
		HandleForgotPasswordResult result = null;
		
		try{
			result = userService
					.handleForgotPassword(forgotpasswordForm.getEmail());
		} catch(Exception ex) {
			if(logger.isErrorEnabled()) {
				logger.error(ex.getMessage(), ex);
			}
		}
		
		if (result == null) {
			model.put("message", StatusResult
	 				.convertErrorCode2Message(Constants.GENERAL_FAILURE));
		} else {
			model.put("message", result.getMessage());
		}

		// send the password to given email
		if (result != null
				&& Constants.SUCCESS.equalsIgnoreCase(result.getStatus())) {// only
																			// success
																			// handling
																			// triggers
																			// email
			emailUtil.sendForgotPasswordEmail(forgotpasswordForm.getEmail(), result.getNewpassword());
		}

		// return message to web page
		return "/client/message";
	}

	/**
	 * steps:
	 * 
	 * 1. validate user existence, user status(active/deactive), email/password
	 * 2. read user data into tomcat session object
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String handleLogin(@ModelAttribute("loginForm") LoginForm loginForm,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {

		// (Optional)validate input format

		// clear current session if any
		request.getSession().invalidate();

		// call login service
		LoginResult result = null;
		try{
			result = userService.handleLogin(loginForm);
		} catch(Exception ex) {
			if(logger.isErrorEnabled()) {
				logger.error(ex.getMessage(), ex);
			}
		}
		if (result == null) {
			model.put("message", StatusResult
					.convertErrorCode2Message(Constants.GENERAL_FAILURE));
			return "/client/message";
		} else if (!Constants.SUCCESS.equalsIgnoreCase(result.getStatus())) {
			model.put("message", result.getMessage());
			return "/client/message";
		}

		// populate app server session
		request.getSession().setMaxInactiveInterval(
				Constants.DEFAULT_SESSION_TIMEOUT);
		request.getSession().setAttribute(Constants.SESSION,
				result.getSessionkey());

		// go to home page
		return "/client/home";
	}
	
	@RequestMapping(value = "/contact", method = RequestMethod.POST)
	public String handleContact(@ModelAttribute("contactForm") ContactForm contactForm,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {

		// (Optional)validate input format
		
		// leave a message for whatever user
		try{
			userService.handleLeaveMessage(contactForm);
		} catch(Exception ex) {
			if(logger.isErrorEnabled()) {
				logger.error(ex.getMessage(), ex);
			}
			model.put("message", StatusResult
					.convertErrorCode2Message(Constants.GENERAL_FAILURE));
			return "/client/message";
		}
		
		model.put("message", "感谢您的留言，我们将会尽快与您联系");
		return "/client/message";
	}
	
	@RequestMapping(value = "/checkemailexistence", method = RequestMethod.POST)
	public void checkEmailExistence(
			HttpServletRequest request,
			HttpServletResponse response) {

		PrintWriter out = null;
		try {
			String email = request.getParameter("email");
			Integer expectAlreadyExist = Integer.valueOf(request.getParameter("expectAlreadyExist"));
			if(logger.isDebugEnabled()) {
				logger.debug("#####checking email " + email + " for existence#####");
			}
			Boolean valid = expectAlreadyExist == 1 ? userService.emailTaken(email)
					: !userService.emailTaken(email);
           
			response.setContentType("application/json;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			out.write(valid.toString());

        } catch (Exception e) {
        	
        } finally {
			if (null != out) {
				out.close();
			}
		}
	}
	
	private String getBody(HttpServletRequest request) throws IOException {
		InputStream is = new BufferedInputStream(request.getInputStream());
		int contentLength = request.getContentLength();
		byte[] data = new byte[contentLength];

		int offset = 0;
		while(offset < contentLength) {
		     final int readNow = is.read(data, offset, contentLength - offset);
		     if (readNow == -1) break;   // Unexpected EOF?
		     offset += readNow;
		}
		return new String(data,"UTF-8");
	}
	
}
