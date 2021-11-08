package com.gh.ghdg.sysMgr.bean.entities.system;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gh.ghdg.sysMgr.BaseEntity;
import com.gh.ghdg.sysMgr.bean.Unique;
import com.gh.ghdg.sysMgr.bean.enums.Status;
import lombok.Data;
import org.hibernate.annotations.Table;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "sys_user")
@Table(appliesTo = "sys_user",comment = "管理员用户表")
@Data
public class User extends BaseEntity {

    @NotBlank(message = "用户名")
    @Unique(name = "用户名")
    @Length(max = 40,message = "用户密码不得超过40位")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Length(max = 40, message = "密码长度不超过40位")
    @JsonIgnore // Json输出忽略
    private String password;

    @NotBlank(message = "盐不能为空")
    @Length(max = 8,message = "盐值长度不超过8位")
    @JsonIgnore // Json 忽略
    private String salt;
    
    @Length(max = 15,message = "ipv4地址应为15个字符")
    private String ip;

    @Length(max = 40,message = "用户昵称不超过32位")
    private String nickname;

    private String avatar;

    @Length(max = 11,message = "电话号码不超过11位")
    private String phone;

    @Length(max = 50,message = "邮箱不超过50位")
    private String email;

    @Length(max = 20,message = "性别不得超过20位")
    private String gender;

    @Length(max = 50,message = "备注不超过50字")
    private String remark;

    @Convert(converter = Status.Converter.class)
    private Status status = Status.生效; // 0-生效 1-失效 默认生效
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date lastLoginDate;

    
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<UserRole> userRoles = new ArrayList<>();
    
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<UserMenu> userMenus = new ArrayList<>();
    
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
