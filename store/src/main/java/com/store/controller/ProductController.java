package com.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.store.dao.UserDAO;
import com.store.entity.User;

@Controller
public class ProductController {

	@Autowired
	private UserDAO userDAO;
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String viewHome(ModelMap model) {

		return "/client/home";
	}
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String welcomeAdmin(ModelMap model) {

		User user = userDAO.findById(1L);
		
		return "/admin/home";
	}
}