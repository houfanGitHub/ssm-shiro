package com.ssm.shiro.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssm.shiro.mapper.RoleMapper;
import com.ssm.shiro.pojo.Role;
import com.ssm.shiro.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;
	
	@Override
	public Role findByUid(Integer rid) {
		return roleMapper.selectByPrimaryKey(rid);
	}

}
