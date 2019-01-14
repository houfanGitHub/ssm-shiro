package com.ssm.shiro.realm;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.ssm.shiro.pojo.Permission;
import com.ssm.shiro.pojo.Role;
import com.ssm.shiro.pojo.RolePermissionKey;
import com.ssm.shiro.pojo.User;
import com.ssm.shiro.service.PermissionService;
import com.ssm.shiro.service.RolePermissionKeyService;
import com.ssm.shiro.service.RoleService;
import com.ssm.shiro.service.UserService;

public class UserRealm extends AuthorizingRealm {
	
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private RolePermissionKeyService rolePermissionKeyService;
	@Autowired
	private PermissionService permissionService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Session session = SecurityUtils.getSubject().getSession();
		User user = (User) session.getAttribute("user");
		Role role = null;
		List<Permission> permissionList = null;
		try {
			role = roleService.findByUid(user.getRid());
			
			List<RolePermissionKey> rolePermissionKeyList = rolePermissionKeyService.findByRid(user.getRid());
			
			if(rolePermissionKeyList != null) {
				List<Integer> pidList = new ArrayList<Integer>();
				for (RolePermissionKey rolePermissionKey : rolePermissionKeyList) {
					pidList.add(rolePermissionKey.getPid());
				}
				if(pidList.size() > 0) {
					permissionList = permissionService.findByPidList(pidList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		if(role != null) {
			info.addRole(role.getRolename());
		}
		if(permissionList != null) {
			for(Permission permission : permissionList) {
				info.addStringPermission(permission.getPermissionname());
			}
		}
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();
		User user = null;
		try {
			user = userService.findUserByUserName(username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(user != null) {
			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
					user.getUsername(),
//					user, 
					user.getPassword(), 
					ByteSource.Util.bytes(user.getUsername()), 
					getName());
			
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			session.setAttribute("user", user);
			
			return info;
		}
		return null;
	}
	
	/**
	 * 清空当前用户缓存
	 */
	public void clearCached() {
		PrincipalCollection principals = SecurityUtils.getSubject()
	            .getPrincipals();
	    super.clearCache(principals);
	}
	
}
