package com.gh.ghdg.sysMgr.bean.entities.system;

import com.gh.ghdg.sysMgr.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "sys_user_menu")
public class UserMenu extends BaseEntity {
    private User user;

    private Menu menu;
    
    public UserMenu() {
    }
    
    public UserMenu(User user, Menu menu) {
        this.user = user;
        this.menu = menu;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "用户不能为空")
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "菜单不能为空")
    public Menu getMenu() {
        return menu;
    }
    
    public void setMenu(Menu menu) {
        this.menu = menu;
    }
    
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
