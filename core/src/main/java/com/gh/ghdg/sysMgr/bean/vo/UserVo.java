package com.gh.ghdg.sysMgr.bean.vo;

import com.gh.ghdg.sysMgr.bean.entities.system.User;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UserVo {
    
    private String id;
    private String username;
    private String nickname;
    private String gender;
    private String email;
    private String phone;
    private Long createdDate;
    private Integer status;
    private Long lastLoginDate;
    private List<String> rolename;
    private String remark;
    
    public UserVo() {
    }
    
    public UserVo(String id, String username, String nickname, String gender, String email, String phone, Long createdDate, Integer status, Long lastLoginDate, List<String> rolename, String remark) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.createdDate = createdDate;
        this.status = status;
        this.lastLoginDate = lastLoginDate;
        this.rolename = rolename;
        this.remark = remark;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getNickname() {
        return nickname;
    }
    
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public Long getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public Long getLastLoginDate() {
        return lastLoginDate;
    }
    
    public void setLastLoginDate(Long lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }
    
    public List<String> getRolename() {
        return rolename;
    }
    
    public void setRolename(List<String> rolename) {
        this.rolename = rolename;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
