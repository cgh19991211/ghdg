package com.gh.ghdg.sysMgr.bean.entities.system;

import com.gh.ghdg.sysMgr.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "sys_role_menu_permission")
public class RoleMenuPermission extends BaseEntity {

    private RoleMenu roleMenu;

    private Permission permission;
    
    public RoleMenuPermission() {
    }
    
    public RoleMenuPermission(RoleMenu roleMenu, Permission permission) {
        this.roleMenu = roleMenu;
        this.permission = permission;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "角色菜单不得为空")
    public RoleMenu getRoleMenu() {
        return roleMenu;
    }
    
    public void setRoleMenu(RoleMenu roleMenu) {
        this.roleMenu = roleMenu;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "权限不得为空")
    public Permission getPermission() {
        return permission;
    }
    
    public void setPermission(Permission permission) {
        this.permission = permission;
    }
    
    @Override
    public String toString() {
        return "RoleMenuPermission{" +
                "id='" + id + '\'' +
                ", createdBy=" + createdBy.getUsername() +
                ", createdDate=" + createdDate +
                ", lastModifiedBy=" + lastModifiedBy.getUsername() +
                ", lastModifiedDate=" + lastModifiedDate +
                ", roleMenu=" + roleMenu.getId() +
                ", permission=" + permission.getPermissionName() +
                '}';
    }
}
