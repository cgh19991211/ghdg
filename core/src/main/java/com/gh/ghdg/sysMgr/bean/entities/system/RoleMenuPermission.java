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
@Table(name = "sys_role_menu_permission")
public class RoleMenuPermission extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "角色菜单不得为空")
    private RoleMenu roleMenu;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "权限不得为空")
    private Permission permission;
    
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
