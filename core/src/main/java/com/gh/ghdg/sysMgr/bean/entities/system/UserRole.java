package com.gh.ghdg.sysMgr.bean.entities.system;

import com.gh.ghdg.sysMgr.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "sys_user_role")
public class UserRole extends BaseEntity {

    private User user;

    private Role role;
    
    public UserRole() {
    }
    
    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "用户不得为空")
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "角色不得为空")
    public Role getRole() {
        return role;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }
    
    @Override
    public String toString() {
        return "UserRole{" +
                "id='" + id + '\'' +
                ", createdBy=" + createdBy.getUsername() +
                ", createdDate=" + createdDate +
                ", lastModifiedBy=" + lastModifiedBy.getUsername() +
                ", lastModifiedDate=" + lastModifiedDate +
                ", user=" + user.getUsername() +
                ", role=" + role.getRoleName() +
                '}';
    }
}
