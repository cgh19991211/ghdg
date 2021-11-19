package com.gh.ghdg.sysMgr.bean.vo;

import java.util.Date;
import java.util.List;

public class RoleVo {
    private String id;
    private String pid;
    private String name;
    private String pName;
    private String code;
    private boolean checked;
    List<RoleVo> children;
    private String tips;
    private Integer num;
    private String creator;
    private Date lastModifiedDate;
    
    public RoleVo() {
    }
    
    public RoleVo(String code, String id, String pid, String name, String pName, boolean checked, List<RoleVo> children, String tips, Integer num, String creator, Date lastModifiedDate) {
        this.code = code;
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.pName = pName;
        this.checked = checked;
        this.children = children;
        this.tips = tips;
        this.num = num;
        this.creator = creator;
        this.lastModifiedDate = lastModifiedDate;
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
    
    public String getpName() {
        return pName;
    }
    
    public void setpName(String pName) {
        this.pName = pName;
    }
    
    public String getTips() {
        return tips;
    }
    
    public void setTips(String tips) {
        this.tips = tips;
    }
    
    public Integer getNum() {
        return num;
    }
    
    public void setNum(Integer num) {
        this.num = num;
    }
    
    public String getCreator() {
        return creator;
    }
    
    public void setCreator(String creator) {
        this.creator = creator;
    }
    
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }
    
    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    @Override
    public String toString() {
        return "RoleVo{" +
                "id='" + id + '\'' +
                ", pid='" + pid + '\'' +
                ", name='" + name + '\'' +
                ", pName='" + pName + '\'' +
                ", checked=" + checked +
                ", tips='" + tips + '\'' +
                ", num=" + num +
                ", code=" + code +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}
