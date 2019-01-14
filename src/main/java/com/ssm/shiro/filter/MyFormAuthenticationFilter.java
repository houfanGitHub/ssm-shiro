package com.ssm.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

public class MyFormAuthenticationFilter extends FormAuthenticationFilter {

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		if (isLoginRequest(request, response) && isLoginSubmission(request, response)){
            //退出当前用户
			this.getSubject(request, response).logout();
			//重新登录
			return false;
		}
        return super.isAccessAllowed(request, response, mappedValue);
    }
}
