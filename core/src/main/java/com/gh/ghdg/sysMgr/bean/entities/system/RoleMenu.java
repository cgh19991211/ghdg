package com.gh.ghdg.sysMgr.bean.entities.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gh.ghdg.sysMgr.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sys_role_menu")
public class RoleMenu extends BaseEntity {

    private Role role;

    private Menu menu;

    private List<RoleMenuPermission> roleMenuPermissions = new ArrayList<>();
    
    public RoleMenu() {
    }
    
    public RoleMenu(String id, User createdBy, Date createdDate, User lastModifiedBy, Timestamp lastModifiedDate, Timestamp lastModifiedDate0, Role role, Menu menu, List<RoleMenuPermission> roleMenuPermissions) {
        super(id, createdBy, createdDate, lastModifiedBy, lastModifiedDate, lastModifiedDate0);
        this.role = role;
        this.menu = menu;
        this.roleMenuPermissions = roleMenuPermissions;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "角色不得为空")
    public Role getRole() {
        return role;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "菜单不得为空")
    public Menu getMenu() {
        return menu;
    }
    
    public void setMenu(Menu menu) {
        this.menu = menu;
    }
    
    @JsonIgnore
    @OneToMany(mappedBy = "roleMenu",cascade = CascadeType.ALL)
    public List<RoleMenuPermission> getRoleMenuPermissions() {
        return roleMenuPermissions;
    }
    
    public void setRoleMenuPermissions(List<RoleMenuPermission> roleMenuPermissions) {
        this.roleMenuPermissions = roleMenuPermissions;
    }
    
    @Override
    public String toString() {
        return "RoleMenu{" +
                "id='" + id + '\'' +
                ", createdBy=" + createdBy.getUsername() +
                ", createdDate=" + createdDate +
                ", lastModifiedBy=" + lastModifiedBy.getUsername() +
                ", lastModifiedDate=" + lastModifiedDate +
                ", lastModifiedDate0=" + lastModifiedDate0 +
                ", role=" + role.getRoleName() +
                ", menu=" + menu.getMenuName() +
                ", roleMenuPermissions=" + roleMenuPermissions +
                '}';
    }
}
