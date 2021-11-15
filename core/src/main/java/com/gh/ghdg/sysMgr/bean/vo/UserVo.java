package com.gh.ghdg.sysMgr.bean.vo;

import com.gh.ghdg.sysMgr.bean.entities.system.User;

import java.util.Date;
import java.util.List;

public class UserVo {
    private String username;
    private String nickname;
    private String gender;
    private String email;
    private String phone;
    private Date createdDate;
    private Integer status;
    private Date lastLoginDate;
    private List<String> rolename;
    
    public UserVo() {
    }
    
    public UserVo(String username, String nickname, String gender, String email, String phone, Date createdDate, Integer status, Date lastLoginDate, List<String> rolename) {
        this.username = username;
        this.nickname = nickname;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.createdDate = createdDate;
        this.status = status;
        this.lastLoginDate = lastLoginDate;
        this.rolename = rolename;
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
    
    public Date getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public Date getLastLoginDate() {
        return lastLoginDate;
    }
    
    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }
    
    public List<String> getRolename() {
        return rolename;
    }
    
    public void setRolename(List<String> rolename) {
        this.rolename = rolename;
    }
}
