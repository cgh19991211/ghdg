package com.gh.ghdg.sysMgr.bean.entities.system;

import com.gh.ghdg.common.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "sys_user_role")
public class UserRole extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "用户不得为空")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "角色不得为空")
    private Role role;
    
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
