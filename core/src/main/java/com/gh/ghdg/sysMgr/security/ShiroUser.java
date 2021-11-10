package com.gh.ghdg.sysMgr.security;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class ShiroUser implements Serializable {
    private static long serialVersionUID = 1L;
    /**
     * menus, name, profile, roleName, roleCodes, permissions
     */
    private String id;
    private String account;      // 用户名，既账号
    private String password;
    private String nickname;         // 昵称
    private List<ShiroRole> roles; // 角色集
    private Set<ShiroMenu> menus;//菜单（封装成waimai中的menu）带资源路径
    private Set<ShiroPermission> permissions;//资源编码
    
    public ShiroUser() {
    }
    
    public ShiroUser(String id, String account, String password, String nickname, List<ShiroRole> roles, Set<ShiroMenu> menus, Set<ShiroPermission> permissions) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.nickname = nickname;
        this.roles = roles;
        this.menus = menus;
        this.permissions = permissions;
    }
    
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    
    public static void setSerialVersionUID(long serialVersionUID) {
        ShiroUser.serialVersionUID = serialVersionUID;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getAccount() {
        return account;
    }
    
    public void setAccount(String account) {
        this.account = account;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getNickname() {
        return nickname;
    }
    
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    
    public List<ShiroRole> getRoles() {
        return roles;
    }
    
    public void setRoles(List<ShiroRole> roles) {
        this.roles = roles;
    }
    
    public Set<ShiroMenu> getMenus() {
        return menus;
    }
    
    public void setMenus(Set<ShiroMenu> menus) {
        this.menus = menus;
    }
    
    public Set<ShiroPermission> getPermissions() {
        return permissions;
    }
    
    public void setPermissions(Set<ShiroPermission> permissions) {
        this.permissions = permissions;
    }
    
    @Override
    public String toString() {
        return "ShiroUser{" +
                "id='" + id + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", roles=" + roles +
                ", menus=" + menus +
                ", permissions=" + permissions +
                '}';
    }
}
