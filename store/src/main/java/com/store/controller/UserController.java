package com.store.controller;

import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
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
import com.store.web.form.ForgotpasswordForm;
import com.store.web.form.LoginForm;
import com.store.web.form.SignUpForm;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	// @Autowired
	// private SessionService sessionService;

	@Autowired
	private Validator validator;

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String handleSignUp(
			@ModelAttribute("signupForm") SignUpForm signupForm,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// clear app server session
		request.getSession().invalidate();

		// (optional)validate input format
		Set<ConstraintViolation<SignUpForm>> constraintViolations = validator
				.validate(signupForm);
		if (constraintViolations.size() > 0) {
			return "/client/signup";// should not hit here
		}

		// (optional)validate if username and email already exists in database

		// create a new user in database
		CreateUserResult result = null;
		try {
			result = userService.createUser(signupForm);
		} catch (Exception e) {
			result = new CreateUserResult(Constants.GENERAL_FAILURE);
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

		// *create a new user on service servers, and get the profile file

		// *send email notification with attached profile file(async)

		// return necessary message to web page according to error code
		model.put("message", result.getMessage());
		model.put("user", signupForm);
		return "/client/message";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String viewSignUp(HttpServletRequest request,
			HttpServletResponse response) {
		return "/client/signup";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String handleLogout(HttpServletRequest request,
			HttpServletResponse response) {

		// clear database session
		String sessionkey = (String) request.getSession().getAttribute(
				Constants.SESSION);
		// sessionService.invalidateSession(sessionkey);

		// clear app server session
		request.getSession().invalidate();

		// go back to home page
		return "redirect:/client/index";
	}

	@RequestMapping(value = "/forgotpassword", method = RequestMethod.GET)
	public String viewForgotPassard(HttpServletRequest request,
			HttpServletResponse response) {

		// go back to home page
		return "redirect:/client/forgotpassword";
	}

	@RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
	public String handleForgotPassard(
			@ModelAttribute("forgotpasswordForm") ForgotpasswordForm forgotpasswordForm,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {

		// (Optional)validate email format

		// change to a machine-generated password
		HandleForgotPasswordResult result = userService
				.handleForgotPassword(forgotpasswordForm.getEmail());
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
			// todo
		}

		// return message to web page
		return "/client/message";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String viewLogin(HttpServletRequest request,
			HttpServletResponse response) {

		return "/client/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String handleLogin(@ModelAttribute("loginForm") LoginForm loginForm,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {

		// (Optional)validate input format

		// clear current session if any
		request.getSession().invalidate();

		// call login service
		LoginResult result = userService.handleLogin(loginForm);
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
		return "/client/index";
	}
}
