package com.gh.ghdg.sysMgr.bean.entities.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gh.ghdg.sysMgr.bean.TreeEntity;
import com.gh.ghdg.sysMgr.bean.Unique;
import lombok.Data;
import org.hibernate.annotations.Table;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "sys_role")
@Table(appliesTo = "sys_role",comment = "角色表")
@Data
public class Role extends TreeEntity<Role> {
    public Role() {
        ignoreChildren = false;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Role parent;

    @NotBlank(message = "角色名称不得为空")
    @Length(max = 40, message = "角色名称不得超过 40 位")
    @Unique(name = "角色名称") // , extraFields = "company")
    private String roleName;

    @NotBlank(message = "角色代码不得为空")
    @Length(max = 40, message = "角色代码不得超过 40 位")
    @Unique(name = "角色代码") // , extraFields = "company")
    private String roleCode;

    @Length(max = 50, message = "备注不得超过 50 位")
    private String remark;

    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private List<UserRole> userRoles = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private List<RoleMenu> roleMenus = new ArrayList<>();


    @Transient
    private List<Permission> permissions = new ArrayList<>();

    @Transient
    private List<Permission> authPermissions = new ArrayList<>();
    
    @Override
    public String toString() {
        return "Role{" +
                "id='" + id + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", lastModifiedDate=" + lastModifiedDate +
                ", lastModifiedDate0=" + lastModifiedDate0 +
                ", displaySeq=" + displaySeq +
                ", roleName='" + roleName + '\'' +
                ", roleCode='" + roleCode + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
