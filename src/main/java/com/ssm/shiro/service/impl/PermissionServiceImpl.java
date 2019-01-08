package com.ssm.shiro.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssm.shiro.mapper.PermissionMapper;
import com.ssm.shiro.pojo.Permission;
import com.ssm.shiro.pojo.PermissionExample;
import com.ssm.shiro.pojo.PermissionExample.Criteria;
import com.ssm.shiro.service.PermissionService;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionMapper permissionMapper;
	
	@Override
	public List<Permission> findByPidList(List<Integer> pidList) {
		PermissionExample example = new PermissionExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andPidIn(pidList);
		return permissionMapper.selectByExample(example);
	}

}
