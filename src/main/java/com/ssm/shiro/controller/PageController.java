package com.ssm.shiro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
	
	@RequestMapping("/")
	public String main() {
		return "main";
	}
 
	@RequestMapping("/page/{returnPage}")
	public String page(@PathVariable(value="returnPage")String returnPage) {
		return returnPage;
	}
}
