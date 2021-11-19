package com.gh.ghdg.sysMgr.bean.vo;

import java.util.List;

public class RoleVo {
    private String id;
    private String pid;
    private String name;
    private boolean checked;
    List<RoleVo> children;
    
    public RoleVo() {
    }
    
    public RoleVo(String id, String pid, String name, boolean checked, List<RoleVo> children) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.checked = checked;
        this.children = children;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getPid() {
        return pid;
    }
    
    public void setPid(String pid) {
        this.pid = pid;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public boolean isChecked() {
        return checked;
    }
    
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    
    public List<RoleVo> getChildren() {
        return children;
    }
    
    public void setChildren(List<RoleVo> children) {
        this.children = children;
    }
    
    @Override
    public String toString() {
        return "RoleVo{" +
                "id='" + id + '\'' +
                ", pid='" + pid + '\'' +
                ", name='" + name + '\'' +
                ", checked=" + checked +
                '}';
    }
}
