package com.gh.ghdg.sysMgr.bean.dto;

import java.util.List;

public class PermissionDto {
    private String id;
    private String name;
    private String code;
    private String url;
    private String pid;
    private String pName;
    private String menuId;
    private String menuName;
    private Integer displaySeq;
    private List<PermissionDto> children;
    private String remark;
    private boolean expanded;
    
    public PermissionDto() {
    }
    
    public PermissionDto(String id, String name, String code, String url, String pid, String menuName, String pName, String menuId, Integer displaySeq, List<PermissionDto> children, String remark, boolean expanded) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.url = url;
        this.pid = pid;
        this.pName = pName;
        this.menuName = menuName;
        this.menuId = menuId;
        this.displaySeq = displaySeq;
        this.children = children;
        this.remark = remark;
        this.expanded = expanded;
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
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getPid() {
        return pid;
    }
    
    public void setPid(String pid) {
        this.pid = pid;
    }
    
    public String getpName() {
        return pName;
    }
    
    public void setpName(String pName) {
        this.pName = pName;
    }
    
    public Integer getDisplaySeq() {
        return displaySeq;
    }
    
    public void setDisplaySeq(Integer displaySeq) {
        this.displaySeq = displaySeq;
    }
    
    public List<PermissionDto> getChildren() {
        return children;
    }
    
    public void setChildren(List<PermissionDto> children) {
        this.children = children;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public String getMenuId() {
        return menuId;
    }
    
    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
    
    public String getMenuName() {
        return menuName;
    }
    
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
    
    public boolean isExpanded() {
        return expanded;
    }
    
    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
    
    @Override
    public String toString() {
        return "PermissionDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", url='" + url + '\'' +
                ", pid='" + pid + '\'' +
                ", pName='" + pName + '\'' +
                ", menuId='" + menuId + '\'' +
                ", menuName='" + menuName + '\'' +
                ", displaySeq=" + displaySeq +
                ", children=" + children +
                ", remark='" + remark + '\'' +
                ", expanded=" + expanded +
                '}';
    }
}
