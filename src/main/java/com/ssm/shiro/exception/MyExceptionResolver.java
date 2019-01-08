package com.ssm.shiro.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class MyExceptionResolver implements HandlerExceptionResolver{
	 
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		System.out.print("捕捉到异常为:"+ex.getClass().getName());
		//捕捉shiro权限异常
		if(ex instanceof UnauthorizedException){
			System.out.println("已配置异常");
			ModelAndView mv = new ModelAndView("unauthorized");
			return mv;
		}
		System.out.println("未配置异常");
		ex.printStackTrace();
		ModelAndView mv = new ModelAndView("error");
		mv.addObject("exception", ex.toString().replaceAll("\n", "<br/>"));
		return mv;
	}
 
}

