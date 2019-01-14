package com.ssm.shiro.pojo;

public class RolePermissionKey {
    private Integer rid;

    private Integer pid;
    
    public RolePermissionKey() {
		super();
	}

	public RolePermissionKey(Integer rid, Integer pid) {
		super();
		this.rid = rid;
		this.pid = pid;
	}

	public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

	@Override
	public String toString() {
		return "RolePermissionKey [rid=" + rid + ", pid=" + pid + "]";
	}
    
}