package com.gh.ghdg.sysMgr.bean.dto;

import java.util.List;

public class MenuDto {
    private String id;
    private String name;
    private String pid;
    private boolean checked;
    private List<MenuDto> children;
    private List<String> permissionIds;
    public MenuDto() {
    }
    
    public MenuDto(String id, String name, String pid, boolean checked, List<MenuDto> children, List<String> permissionIds) {
        this.id = id;
        this.name = name;
        this.pid = pid;
        this.checked = checked;
        this.children = children;
        this.permissionIds = permissionIds;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPid() {
        return pid;
    }
    
    public void setPid(String pid) {
        this.pid = pid;
    }
    
    public boolean isChecked() {
        return checked;
    }
    
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    
    public List<MenuDto> getChildren() {
        return children;
    }
    
    public void setChildren(List<MenuDto> children) {
        this.children = children;
    }
    
    public List<String> getPermissionIds() {
        return permissionIds;
    }
    
    public void setPermissionIds(List<String> permissionIds) {
        this.permissionIds = permissionIds;
    }
    
    @Override
    public String toString() {
        return "MenuDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", pid='" + pid + '\'' +
                ", checked=" + checked +
                '}';
    }
}