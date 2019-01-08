package com.ssm.shiro.service.impl;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssm.shiro.mapper.RolePermissionMapper;
import com.ssm.shiro.pojo.RolePermissionExample;
import com.ssm.shiro.pojo.RolePermissionExample.Criteria;
import com.ssm.shiro.pojo.RolePermissionKey;
import com.ssm.shiro.service.RolePermissionKeyService;

@Service
@Transactional
public class RolePermissionKeyServiceImpl implements RolePermissionKeyService {

	@Autowired
	private RolePermissionMapper rolePermissionMapper;
	
	@Override
	public List<RolePermissionKey> findByRid(Integer rid) {
		RolePermissionExample example = new RolePermissionExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andRidEqualTo(rid);
		List<RolePermissionKey> selectByExample = rolePermissionMapper.selectByExample(example);
		return selectByExample;
	}

}
