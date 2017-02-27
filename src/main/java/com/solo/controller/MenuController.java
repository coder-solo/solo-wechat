package com.solo.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller(value = "/menu")
public class MenuController {

	private final static Logger logger = Logger.getLogger(MenuController.class);

	@RequestMapping(value = "")
	public String showPage(Model model) {
		logger.info("showPage");
		return "menu";
	}
}
