package com.gh.ghdg.sysMgr.bean.entities.system;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gh.ghdg.sysMgr.BaseEntity;
import com.gh.ghdg.sysMgr.bean.Unique;
import com.gh.ghdg.sysMgr.bean.enums.Status;
import org.hibernate.annotations.Table;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "sys_user")
@Table(appliesTo = "sys_user",comment = "管理员用户表")
public class User extends BaseEntity {

    private String username;

    private String password;

    private String salt;
    
    private String ip;

    private String nickname;

    private String avatar;

    private String phone;

    private String email;

    private String gender;

    private String remark;

    private Status status = Status.生效; // 0-生效 1-失效 默认生效
    
    private Date lastLoginDate;

    
    private List<UserRole> userRoles = new ArrayList<>();
    
    private List<UserMenu> userMenus = new ArrayList<>();
    
    public User() {
    }
    
    public User(String username, String password, String salt, String ip, String nickname, String avatar, String phone, String email, String gender, String remark, Status status, Date lastLoginDate, List<UserRole> userRoles, List<UserMenu> userMenus) {
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.ip = ip;
        this.nickname = nickname;
        this.avatar = avatar;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.remark = remark;
        this.status = status;
        this.lastLoginDate = lastLoginDate;
        this.userRoles = userRoles;
        this.userMenus = userMenus;
    }
    
    @NotBlank(message = "用户名")
    @Unique(name = "用户名")
    @Length(max = 40,message = "用户密码不得超过40位")
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    @NotBlank(message = "密码不能为空")
    @Length(max = 40, message = "密码长度不超过40位")
    @JsonIgnore // Json输出忽略
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    @NotBlank(message = "盐不能为空")
    @Length(max = 8,message = "盐值长度不超过8位")
    @JsonIgnore // Json 忽略
    public String getSalt() {
        return salt;
    }
    
    public void setSalt(String salt) {
        this.salt = salt;
    }
    
    @Length(max = 15,message = "ipv4地址应为15个字符")
    public String getIp() {
        return ip;
    }
    
    public void setIp(String ip) {
        this.ip = ip;
    }
    
    @Length(max = 40,message = "用户昵称不超过32位")
    public String getNickname() {
        return nickname;
    }
    
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    
    public String getAvatar() {
        return avatar;
    }
    
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    
    @Length(max = 11,message = "电话号码不超过11位")
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    @Length(max = 50,message = "邮箱不超过50位")
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Length(max = 20,message = "性别不得超过20位")
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    @Length(max = 50,message = "备注不超过50字")
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    @Convert(converter = Status.Converter.class)
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    public Date getLastLoginDate() {
        return lastLoginDate;
    }
    
    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }
    
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    public List<UserRole> getUserRoles() {
        return userRoles;
    }
    
    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
    
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    public List<UserMenu> getUserMenus() {
        return userMenus;
    }
    
    public void setUserMenus(List<UserMenu> userMenus) {
        this.userMenus = userMenus;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", ip='" + ip + '\'' +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                ", lastLoginDate=" + lastLoginDate +
                '}';
    }
}
