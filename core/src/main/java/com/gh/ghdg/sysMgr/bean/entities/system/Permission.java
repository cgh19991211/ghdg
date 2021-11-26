package com.gh.ghdg.sysMgr.bean.entities.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gh.ghdg.sysMgr.bean.TreeEntity;
import com.gh.ghdg.sysMgr.bean.Unique;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sys_permission")
public class Permission extends TreeEntity<Permission> {
    
    public Permission(){
        ignoreChildren = false;
    }
    
    protected Permission parent;
    
    private String permissionName;
    
    private String permissionCode;
    
    private String url;
    
    private String remark;

    private Menu menu;

    private List<RoleMenuPermission> roleMenuPermissions = new ArrayList<>();
    
    public Permission(Permission parent, String permissionName, String permissionCode, String url, String remark, Menu menu, List<RoleMenuPermission> roleMenuPermissions) {
        this.parent = parent;
        this.permissionName = permissionName;
        this.permissionCode = permissionCode;
        this.url = url;
        this.remark = remark;
        this.menu = menu;
        this.roleMenuPermissions = roleMenuPermissions;
    }
    
    @Override
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "parent_id",referencedColumnName = "id")
    public Permission getParent() {
        return parent;
    }
    
    @Override
    public void setParent(Permission parent) {
        this.parent = parent;
    }
    
    @NotBlank(message = "操作名称不得为空")
    @Length(max = 40, message = "操作名称不得超过 40 位")
    @Unique(name = "操作名称", extraFields = "menu")
    public String getPermissionName() {
        return permissionName;
    }
    
    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }
    
    @NotBlank(message = "操作代码不得为空")
    @Length(max = 40, message = "操作代码不得超过 40 位")
    @Unique(name = "操作代码", extraFields = "menu")
    public String getPermissionCode() {
        return permissionCode;
    }
    
    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }
    
    @Unique(name = "资源路径")
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    @Length(max = 50, message = "备注不得超过 50 位")
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    @JsonIgnoreProperties({"permission"})
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "menu_id",referencedColumnName = "id")
    public Menu getMenu() {
        return menu;
    }
    
    public void setMenu(Menu menu) {
        this.menu = menu;
    }
    
    @JsonIgnore
    @OneToMany(mappedBy = "permission",cascade = CascadeType.ALL)
    public List<RoleMenuPermission> getRoleMenuPermissions() {
        return roleMenuPermissions;
    }
    
    public void setRoleMenuPermissions(List<RoleMenuPermission> roleMenuPermissions) {
        this.roleMenuPermissions = roleMenuPermissions;
    }
    
    @Override
    public String toString() {
        return "Permission{" +
                "id='" + id + '\'' +
                ", createdBy='" + createdBy.getUsername() + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedBy='" + lastModifiedBy.getUsername() + '\'' +
                ", lastModifiedDate=" + lastModifiedDate +
                ", permissionName='" + permissionName + '\'' +
                ", permissionCode='" + permissionCode + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
    
}
