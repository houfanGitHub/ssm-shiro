package com.ssm.shiro.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssm.shiro.mapper.RoleMapper;
import com.ssm.shiro.pojo.Role;
import com.ssm.shiro.pojo.RoleExample;
import com.ssm.shiro.pojo.RoleExample.Criteria;
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

	@Override
	public Role findByRoleName(String roleName) {
		RoleExample example = new RoleExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andRolenameEqualTo(roleName);
		List<Role> selectByExample = roleMapper.selectByExample(example);
		if(selectByExample != null && selectByExample.size() > 0) {
			return selectByExample.get(0);
		}
		return null;
	}

}
