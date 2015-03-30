package com.store.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.store.result.StatusResult;
import com.store.service.UserService;
import com.store.utils.Constants;

@Controller
public class UserReadOnlyController {

	private static final Logger logger = LoggerFactory
			.getLogger(UserReadOnlyController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private Validator validator;

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String viewSignUp(HttpServletRequest request,
			HttpServletResponse response) {
		return "/client/signup";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String handleLogout(HttpServletRequest request,
			HttpServletResponse response) {

		// clear app server session
		request.getSession().invalidate();

		// go back to home page
		return "/client/home";
	}

	@RequestMapping(value = "/forgotpassword", method = RequestMethod.GET)
	public String viewForgotPassard(HttpServletRequest request,
			HttpServletResponse response) {

		// go back to home page
		return "/client/forgotpassword";
	}

	@RequestMapping(value = "/changepassword", method = RequestMethod.GET)
	public String viewChangePassard(HttpServletRequest request,
			HttpServletResponse response) {

		return "/client/changepass";
	}
	
	@RequestMapping(value = "/changepasswithcode", method = RequestMethod.GET)
	public String viewChangePassardWithCode(HttpServletRequest request,
			HttpServletResponse response) {

		return "/client/changepasswithcode";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String viewLogin(HttpServletRequest request,
			HttpServletResponse response) {
		return "/client/login";
	}

	@RequestMapping(value = "/account", method = RequestMethod.GET)
	public String viewAccount(ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {

		model.put("email", request.getSession().getAttribute(Constants.EMAIL));
		return "/client/account";
	}

	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public String viewContact(HttpServletRequest request,
			HttpServletResponse response) {
		return "/client/contact";
	}

}
