package com.gh.ghdg.sysMgr.bean.entities.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gh.ghdg.sysMgr.bean.TreeEntity;
import com.gh.ghdg.sysMgr.bean.Unique;
import org.hibernate.annotations.Table;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "sys_role")
@Table(appliesTo = "sys_role",comment = "角色表")
public class Role extends TreeEntity<Role> {
    
    public Role() {
        ignoreChildren = false;
    }

    private Role parent;

    private String roleName;

    private String roleCode;

    
    private String remark;

    private List<UserRole> userRoles = new ArrayList<>();

    private List<RoleMenu> roleMenus = new ArrayList<>();


    private List<Permission> permissions = new ArrayList<>();

//    @Transient
//    private List<Permission> authPermissions = new ArrayList<>();
    
    public Role(Role parent, String roleName, String roleCode, String remark, List<UserRole> userRoles, List<RoleMenu> roleMenus, List<Permission> permissions) {
        this.parent = parent;
        this.roleName = roleName;
        this.roleCode = roleCode;
        this.remark = remark;
        this.userRoles = userRoles;
        this.roleMenus = roleMenus;
        this.permissions = permissions;
    }
    
    @Override
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "parent_id",referencedColumnName = "id")
    public Role getParent() {
        return parent;
    }
    
    @Override
    public void setParent(Role parent) {
        this.parent = parent;
    }
    
    @NotBlank(message = "角色名称不得为空")
    @Length(max = 40, message = "角色名称不得超过 40 位")
    @Unique(name = "角色名称") // , extraFields = "company")
    public String getRoleName() {
        return roleName;
    }
    
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
    @NotBlank(message = "角色代码不得为空")
    @Length(max = 40, message = "角色代码不得超过 40 位")
    @Unique(name = "角色代码") // , extraFields = "company")
    public String getRoleCode() {
        return roleCode;
    }
    
    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
    
    @Length(max = 50, message = "备注不得超过 50 位")
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    @JsonIgnore
    @OneToMany(mappedBy = "role")
    public List<UserRole> getUserRoles() {
        return userRoles;
    }
    
    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
    
    @JsonIgnore
    @OneToMany(mappedBy = "role")
    public List<RoleMenu> getRoleMenus() {
        return roleMenus;
    }
    
    public void setRoleMenus(List<RoleMenu> roleMenus) {
        this.roleMenus = roleMenus;
    }
    
    @Transient
    public List<Permission> getPermissions() {
        return permissions;
    }
    
    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
    
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
