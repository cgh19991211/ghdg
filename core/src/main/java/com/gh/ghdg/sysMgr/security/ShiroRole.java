package com.gh.ghdg.sysMgr.security;


import java.io.Serializable;

public class ShiroRole implements Serializable {
    private  static long serialVersionUID = 1L;
    
    private String id;
    private String roleName;
    private String roleCode;
    
    public ShiroRole() {
    }
    
    public ShiroRole(String id, String roleName, String roleCode) {
        this.id = id;
        this.roleName = roleName;
        this.roleCode = roleCode;
    }
    
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    
    public static void setSerialVersionUID(long serialVersionUID) {
        ShiroRole.serialVersionUID = serialVersionUID;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getRoleName() {
        return roleName;
    }
    
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
    public String getRoleCode() {
        return roleCode;
    }
    
    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
    
    @Override
    public String toString() {
        return "ShiroRole{" +
                "id='" + id + '\'' +
                ", roleName='" + roleName + '\'' +
                ", roleCode='" + roleCode + '\'' +
                '}';
    }
}
