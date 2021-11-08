package com.gh.ghdg.sysMgr.bean.entities.system;

import com.gh.ghdg.sysMgr.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "sys_user_menu")
public class UserMenu extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "用户不能为空")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "菜单不能为空")
    private Menu menu;
    
    @Override
    public String toString() {
        return "UserMenu{" +
                "id='" + id + '\'' +
                ", createdBy=" + createdBy.getUsername() +
                ", createdDate=" + createdDate +
                ", lastModifiedBy=" + lastModifiedBy.getUsername() +
                ", lastModifiedDate=" + lastModifiedDate +
                ", user=" + user.getUsername() +
                ", menu=" + menu.getMenuName() +
                '}';
    }
}
