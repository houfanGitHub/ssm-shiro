package com.ssm.shiro.service.impl;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.javassist.expr.NewArray;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssm.shiro.mapper.RolePermissionMapper;
import com.ssm.shiro.pojo.RolePermissionExample;
import com.ssm.shiro.pojo.RolePermissionExample.Criteria;
import com.ssm.shiro.pojo.RolePermissionKey;
import com.ssm.shiro.pojo.User;
import com.ssm.shiro.realm.UserRealm;
import com.ssm.shiro.service.RolePermissionKeyService;

@Service
@Transactional
public class RolePermissionKeyServiceImpl implements RolePermissionKeyService {

	@Autowired
	private RolePermissionMapper rolePermissionMapper;
	@Autowired
	private EhCacheManager cacheManager;
	
	@Override
	public List<RolePermissionKey> findByRid(Integer rid) {
		RolePermissionExample example = new RolePermissionExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andRidEqualTo(rid);
		List<RolePermissionKey> selectByExample = rolePermissionMapper.selectByExample(example);
		return selectByExample;
	}

	@Override
	public void updatePermission(String username, int rid, int[] permissions) {
		RolePermissionExample example = new RolePermissionExample();
		example.createCriteria().andRidEqualTo(rid);
		rolePermissionMapper.deleteByExample(example);
		/*List<RolePermissionKey> selectByExample = rolePermissionMapper.selectByExample(null);
		for (RolePermissionKey rolePermissionKey : selectByExample) {
			System.out.println(rolePermissionKey.toString());
		}*/
		if(permissions != null) {
			for (int pid : permissions) {
				rolePermissionMapper.insert(new RolePermissionKey(rid, pid));
			}
		}
		
		//清除缓存
		Cache<SimplePrincipalCollection, Object> cache = cacheManager.getCache(UserRealm.class.getName()+".authorizationCache");
		
		Set<SimplePrincipalCollection> keys = cache.keys();
		if(keys != null && keys.size() > 0) {
			for (SimplePrincipalCollection simplePrincipalCollection : keys) {
				if(username.equals(simplePrincipalCollection.getPrimaryPrincipal().toString())) {
					cache.remove(simplePrincipalCollection);
					break;
				}
			}
		}
		
	}

}
