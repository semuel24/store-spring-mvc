package com.store.controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.validation.ConstraintViolation;
//import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.store.result.CreateUserResult;
import com.store.service.SessionService;
import com.store.service.UserService;
import com.store.utils.Constants;
import com.store.web.form.SignUpForm;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private SessionService sessionService;

	// @Autowired
	// private Validator validator;

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String processSignUp(
			@ModelAttribute("signupForm") SignUpForm signupForm,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// clear app server session
		request.getSession().invalidate();
				
		// (optional)validate input format
		// Set<ConstraintViolation<SignUpForm>> constraintViolations = validator
		// .validate(signupForm);
		// if (constraintViolations.size() > 0) {
		// return "/client/signup";// should not hit here
		// }

		// (optional)validate if username and email already exists in database

		// create a new user in database
		CreateUserResult result = null;
		try {
			result = userService.createUser(signupForm);
		} catch (Exception e) {
			result = new CreateUserResult(Constants.GENERAL_FAILURE);
		}
		if (!Constants.SUCCESS.equalsIgnoreCase(result.getStatus())) {
			model.put("message", result.getMessage());
			return "/client/message";
		}

		// populate session
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
	public String logout(HttpServletRequest request,
			HttpServletResponse response) {

		// clear database session
		String sessionkey = (String) request.getSession().getAttribute(
				Constants.SESSION);
		sessionService.invalidateSession(sessionkey);

		// clear app server session
		request.getSession().invalidate();

		// go back to home page
		return "redirect:/client/index";
	}

}
