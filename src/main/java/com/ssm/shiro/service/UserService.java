package com.ssm.shiro.service;

import java.util.List;

import com.ssm.shiro.pojo.User;

public interface UserService {

	User findUserByUserName(String username);

	List<User> findUserByNotRid(Integer rid);

}
