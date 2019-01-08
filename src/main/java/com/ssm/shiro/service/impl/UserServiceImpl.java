package com.ssm.shiro.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssm.shiro.mapper.UserMapper;
import com.ssm.shiro.pojo.User;
import com.ssm.shiro.pojo.UserExample;
import com.ssm.shiro.pojo.UserExample.Criteria;
import com.ssm.shiro.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public User findUserByUserName(String username) {
		UserExample example = new UserExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andUsernameEqualTo(username);
		List<User> selectByExample = userMapper.selectByExample(example);
		if(selectByExample!= null &&selectByExample.size()>0) {
			return selectByExample.get(0);
		}
		return null;
	}

}
