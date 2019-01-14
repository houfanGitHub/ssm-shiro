package com.ssm.shiro.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.shiro.pojo.Permission;
import com.ssm.shiro.pojo.Role;
import com.ssm.shiro.pojo.RolePermissionKey;
import com.ssm.shiro.pojo.User;
import com.ssm.shiro.service.PermissionService;
import com.ssm.shiro.service.RolePermissionKeyService;
import com.ssm.shiro.service.RoleService;
import com.ssm.shiro.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private RolePermissionKeyService rolePermissionKeyService;

	@RequestMapping("/login")
	public String login(HttpServletRequest request,Map<String, Object> map) {
		String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");
		String errorMsg = null;
		if(UnknownAccountException.class.getName().equals(exceptionClassName)) {
			System.out.println("账号不存在");
			errorMsg = "账号或密码错误";
		}else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
			System.out.println("密码错误");
			errorMsg = "账号或密码错误";
		}else if (exceptionClassName == null) {
			System.out.println("页面跳转");
		}
		map.put("errorMsg", errorMsg);
		return "login";
	}
	
	@RequestMapping("/insert")
	@RequiresPermissions("user:insert")
	public String insert() {
		return "insert";
	}
	
	@RequestMapping("/select")
	@RequiresPermissions("user:select")
	public String select() {
		return "select";
	}
	
	@RequestMapping("/update")
	@RequiresPermissions("user:update")
	public String update(Map<String, Object> map) {
		//查询管理员rid
		Role role = roleService.findByRoleName("admin");
		//查询所有非管理员的用户
		List<User> customersList = null;
		if(role != null) {
			customersList = userService.findUserByNotRid(role.getRid());
		}
		//查询所有权限
		List<Permission> permissions = permissionService.findAll();
		
		map.put("customersList", customersList);
		map.put("permissions", permissions);
		
		return "update";
	}
	
	@RequestMapping("/findPermissions/{rid}")
	@ResponseBody
	public List<Permission> findPermissions(@PathVariable(value="rid")int rid){
		//根据rid查询所有权限
		List<Permission> permissionList = null;
		try {
			List<RolePermissionKey> rolePermissionKeyList = rolePermissionKeyService.findByRid(rid);
			
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
		return permissionList;
	}
	
	@RequestMapping("/updatePermission")
	@ResponseBody
	public boolean updatePermission(String username, int rid, int[] permissions) {
		//根据角色id修改权限
		if(rid != 0 && username != null) {
			try {
				rolePermissionKeyService.updatePermission(username, rid, permissions);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	@RequestMapping("/delete")
	@RequiresPermissions("user:delete")
	public String delete() {
		return "delete";
	}
}
