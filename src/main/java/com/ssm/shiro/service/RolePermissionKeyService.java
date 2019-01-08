package com.ssm.shiro.service;

import java.util.List;

import com.ssm.shiro.pojo.RolePermissionKey;

public interface RolePermissionKeyService {

	List<RolePermissionKey> findByRid(Integer rid);

}
