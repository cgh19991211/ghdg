package com.gh.ghdg.sysMgr.bean.entities.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gh.ghdg.sysMgr.bean.TreeEntity;
import com.gh.ghdg.sysMgr.bean.Unique;
import com.gh.ghdg.common.enums.Status;
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

    private Permission permission;

    private List<RoleMenu> roleMenus = new ArrayList<>();

    private List<UserMenu> userMenus = new ArrayList<>();

    private boolean navigation; // 导航用
    
    @Transient
    public boolean isLeaf() {
        return navigation ? 0 == children.size() : leaf;
    }

    private Permission authPermission;
    
    public Menu(Menu parent, String menuCode, String menuName, String tip, TypeEnum.MenuType type, Status status, Permission permission, List<RoleMenu> roleMenus, List<UserMenu> userMenus, boolean navigation, Permission authPermission, String icon) {
        this.parent = parent;
        this.menuCode = menuCode;
        this.menuName = menuName;
        this.tip = tip;
        this.type = type;
        this.status = status;
        this.permission = permission;
        this.roleMenus = roleMenus;
        this.userMenus = userMenus;
        this.navigation = navigation;
        this.authPermission = authPermission;
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
    
    @OneToOne(mappedBy = "menu", fetch = FetchType.LAZY)
    @OrderBy("displaySeq")
    public Permission getPermission() {
        return permission;
    }
    
    public void setPermission(Permission permission) {
        this.permission = permission;
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
    public Permission getAuthPermission() {
        return authPermission;
    }
    
    public void setAuthPermission(Permission authPermission) {
        this.authPermission = authPermission;
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
                ", createdBy=" + createdBy +
                ", createdDate=" + createdDate +
                ", lastModifiedBy=" + lastModifiedBy +
                ", lastModifiedDate=" + lastModifiedDate +
                ", displaySeq=" + displaySeq +
                ", parent=" + parent +
                ", menuCode='" + menuCode + '\'' +
                ", menuName='" + menuName + '\'' +
                ", tip='" + tip + '\'' +
                ", icon='" + icon + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", permission=" + permission +
                ", roleMenus=" + roleMenus +
                ", userMenus=" + userMenus +
                ", navigation=" + navigation +
                ", authPermission=" + authPermission +
                '}';
    }
}
