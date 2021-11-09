package com.gh.ghdg.sysMgr.security;

import lombok.Data;

import java.io.Serializable;

public class ShiroPermission implements Serializable {
    private static long serialVersionUID = 1L;
    
    private String permissionCode;
    private String url;
    
    public ShiroPermission() {
    }
    
    public ShiroPermission(String permissionCode, String url) {
        this.permissionCode = permissionCode;
        this.url = url;
    }
    
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    
    public static void setSerialVersionUID(long serialVersionUID) {
        ShiroPermission.serialVersionUID = serialVersionUID;
    }
    
    public String getPermissionCode() {
        return permissionCode;
    }
    
    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    @Override
    public String toString() {
        return "ShiroPermission{" +
                "permissionCode='" + permissionCode + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
