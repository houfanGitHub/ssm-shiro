package com.ssm.shiro.service;

import java.util.List;

import com.ssm.shiro.pojo.Permission;

public interface PermissionService {

	List<Permission> findByPidList(List<Integer> pidList);

}
