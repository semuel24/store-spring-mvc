package com.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.store.dao.UserDAO;
import com.store.entity.User;

@Controller
public class BaseController {

	private static int counter = 0;
	private static final String VIEW_INDEX = "index";

	@Autowired
	private UserDAO userDAO;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcome(ModelMap model) {

		model.addAttribute("message", "Welcome");
		model.addAttribute("counter", ++counter);
//		logger.debug("[welcome] counter : {}", counter);

		// Spring uses InternalResourceViewResolver and return back index.jsp
		
		User user = userDAO.findById(1L);
		
		return VIEW_INDEX;
//		return "/client/index";

	}

	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public String welcomeName(@PathVariable String name, ModelMap model) {

		model.addAttribute("message", "Welcome " + name);
		model.addAttribute("counter", ++counter);
		
		User user = userDAO.findById(1L);
		
		return VIEW_INDEX;

	}
	
	@RequestMapping(value = "/client", method = RequestMethod.GET)
	public String welcomeClient(ModelMap model) {

//		model.addAttribute("message", "Welcome");
//		model.addAttribute("counter", ++counter);
//		logger.debug("[welcome] counter : {}", counter);

		// Spring uses InternalResourceViewResolver and return back index.jsp
		
		User user = userDAO.findById(1L);
		
//		return VIEW_INDEX;
		return "/client/index";

	}
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String welcomeAdmin(ModelMap model) {

//		model.addAttribute("message", "Welcome");
		model.addAttribute("counter", ++counter);
//		logger.debug("[welcome] counter : {}", counter);

		// Spring uses InternalResourceViewResolver and return back index.jsp
		
		User user = userDAO.findById(1L);
		
//		return VIEW_INDEX;
		return "/admin/index";

	}

}