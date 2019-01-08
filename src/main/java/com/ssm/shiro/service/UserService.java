package com.ssm.shiro.service;

import com.ssm.shiro.pojo.User;

public interface UserService {

	User findUserByUserName(String username);

}
