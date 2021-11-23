package com.gh.ghdg.sysMgr.bean.entities.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gh.ghdg.sysMgr.bean.TreeEntity;
import com.gh.ghdg.sysMgr.bean.Unique;
import com.gh.ghdg.sysMgr.bean.enums.Status;
import com.gh.ghdg.sysMgr.bean.enums.TypeEnum;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sys_menu")
public class Menu extends TreeEntity<Menu> {

    public Menu() {
        ignoreChildren = false;
    }

    protected Menu parent;

    private String menuCode;

    private String menuName;
    
    private String tip;
    
    private String icon;
    
    private TypeEnum.MenuType type = TypeEnum.MenuType.menu;//1-menu 0-button 3-module, default 0-button
    
    public Status status = Status.生效;//1-生效 0-失效  默认生效

    private List<Permission> permissions = new ArrayList<>();

    private List<RoleMenu> roleMenus = new ArrayList<>();

    private List<UserMenu> userMenus = new ArrayList<>();

    private boolean navigation; // 导航用
    
    @Transient
    public boolean isLeaf() {
        return navigation ? 0 == children.size() : leaf;
    }

    private List<Permission> authPermissions = new ArrayList<>();
    
    public Menu(Menu parent, String menuCode, String menuName, String tip, TypeEnum.MenuType type, Status status, List<Permission> permissions, List<RoleMenu> roleMenus, List<UserMenu> userMenus, boolean navigation, List<Permission> authPermissions, String icon) {
        this.parent = parent;
        this.menuCode = menuCode;
        this.menuName = menuName;
        this.tip = tip;
        this.type = type;
        this.status = status;
        this.permissions = permissions;
        this.roleMenus = roleMenus;
        this.userMenus = userMenus;
        this.navigation = navigation;
        this.authPermissions = authPermissions;
        this.icon = icon;
    }
    
    @Override
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "parent_id",referencedColumnName = "id")
    public Menu getParent() {
        return parent;
    }
    
    @Override
    public void setParent(Menu parent) {
        this.parent = parent;
    }
    
    @NotBlank(message = "菜单代码不得为空")
    @Length(min = 2, max = 40, message = "菜单代码不得超过 40 位")
    @Unique(name = "菜单代码")
    public String getMenuCode() {
        return menuCode;
    }
    
    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }
    
    @NotBlank(message = "菜单名称不得为空")
    @Length(min = 2, max = 40, message = "菜单名称不得超过 40 位")
    @Unique(name = "菜单名称", extraFields = "parent")
    public String getMenuName() {
        return menuName;
    }
    
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
    
    @Length(max = 30,message = "鼠标悬浮提示不能超过30字")
    public String getTip() {
        return tip;
    }
    
    public void setTip(String tip) {
        this.tip = tip;
    }
    
    @Convert(converter = TypeEnum.MenuType.Converter.class)
    public TypeEnum.MenuType getType() {
        return type;
    }
    
    public void setType(TypeEnum.MenuType type) {
        this.type = type;
    }
    
    @Convert(converter = Status.Converter.class)
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    @OneToMany(mappedBy = "menu", fetch = FetchType.EAGER)
    @OrderBy("displaySeq")
    public List<Permission> getPermissions() {
        return permissions;
    }
    
    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
    
    @JsonIgnore
    @OneToMany(mappedBy = "menu")
    public List<RoleMenu> getRoleMenus() {
        return roleMenus;
    }
    
    public void setRoleMenus(List<RoleMenu> roleMenus) {
        this.roleMenus = roleMenus;
    }
    
    @JsonIgnore
    @OneToMany(mappedBy = "menu")
    public List<UserMenu> getUserMenus() {
        return userMenus;
    }
    
    public void setUserMenus(List<UserMenu> userMenus) {
        this.userMenus = userMenus;
    }
    
    @Transient
    public boolean isNavigation() {
        return navigation;
    }
    
    public void setNavigation(boolean navigation) {
        this.navigation = navigation;
    }
    
    @Transient
    public List<Permission> getAuthPermissions() {
        return authPermissions;
    }
    
    public void setAuthPermissions(List<Permission> authPermissions) {
        this.authPermissions = authPermissions;
    }
    
    public String getIcon() {
        return icon;
    }
    
    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    @Override
    public String toString() {
        return "Menu{" +
                "id='" + id + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                ", parent=" + parent +
                ", menuCode='" + menuCode + '\'' +
                ", menuName='" + menuName + '\'' +
                ", tip='" + tip + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", navigation=" + navigation +
                ", permissions=" + permissions +
                '}';
    }
}
