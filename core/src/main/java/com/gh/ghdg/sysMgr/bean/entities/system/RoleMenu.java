package com.gh.ghdg.sysMgr.bean.entities.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gh.ghdg.sysMgr.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "sys_role_menu")
public class RoleMenu extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "角色不得为空")
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "菜单不得为空")
    private Menu menu;

    @JsonIgnore
    @OneToMany(mappedBy = "roleMenu") // 级联
    private List<RoleMenuPermission> roleMenuPermissions = new ArrayList<>();
    
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
