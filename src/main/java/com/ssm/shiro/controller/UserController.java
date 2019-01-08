package com.ssm.shiro.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

	@RequestMapping("/login")
	public String login(HttpServletRequest request,Map<String, Object> map) {
		String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");
		String errorMsg = null;
		if(UnknownAccountException.class.getName().equals(exceptionClassName)) {
			System.out.println("账号不存在");
			errorMsg = "账号或密码错误";
		}else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
			System.out.println("密码错误");
			errorMsg = "账号或密码错误";
		}else if (exceptionClassName == null) {
			System.out.println("页面跳转");
		}
		map.put("errorMsg", errorMsg);
		return "login";
	}
	
	@RequestMapping("/insert")
	@RequiresPermissions("user:insert")
	public String insert() {
		return "insert";
	}
	
	@RequestMapping("/select")
	@RequiresPermissions("user:select")
	public String select() {
		return "select";
	}
	
	@RequestMapping("/update")
	@RequiresPermissions("user:update")
	public String update() {
		return "update";
	}
	
	@RequestMapping("/delete")
	@RequiresPermissions("user:delete")
	public String delete() {
		return "delete";
	}
}
