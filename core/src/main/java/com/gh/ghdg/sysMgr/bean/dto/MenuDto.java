package com.gh.ghdg.sysMgr.bean.dto;

import java.util.List;

public class MenuDto {
    private String id;
    private String name;
    private String pid;
    private boolean checked;
    private List<MenuDto> children;
    //TODO:添加权限
    private String permissionCode;
    public MenuDto() {
    }
    
    public MenuDto(String id, String name, String pid, boolean checked, List<MenuDto> children) {
        this.id = id;
        this.name = name;
        this.pid = pid;
        this.checked = checked;
        this.children = children;
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
