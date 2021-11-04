package com.gh.ghdg.sysMgr.bean.entities.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gh.ghdg.sysMgr.bean.TreeEntity;
import com.gh.ghdg.sysMgr.bean.Unique;
import com.gh.ghdg.sysMgr.bean.enums.Status;
import com.gh.ghdg.sysMgr.bean.enums.TypeEnum;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "sys_menu")
public class Menu extends TreeEntity<Menu> {

    public Menu() {
        ignoreChildren = false;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    protected Menu parent;

    @NotBlank(message = "菜单代码不得为空")
    @Length(max = 40, message = "菜单代码不得超过 40 位")
    @Unique(name = "菜单代码")
    private String menuCode;

    @NotBlank(message = "菜单名称不得为空")
    @Length(max = 40, message = "菜单名称不得超过 40 位")
    @Unique(name = "菜单名称", extraFields = "parent")
    private String menuName;
    
    @Length(max = 30,message = "鼠标悬浮提示不能超过30字")
    private String tip;
    
    @Convert(converter = TypeEnum.MenuType.Converter.class)
    private TypeEnum.MenuType type = TypeEnum.MenuType.menu;//0-menu 1-button, default 0-menu
    
    @Convert(converter = Status.Converter.class)
    public Status status = Status.生效;//1-生效 0-失效  默认生效

    @OneToMany(mappedBy = "menu", fetch = FetchType.EAGER)
    @OrderBy("displaySeq")
    private List<Permission> permissions = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "menu")
    private List<RoleMenu> roleMenus = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "menu")
    private List<UserMenu> userMenus = new ArrayList<>();

    @Transient
    private boolean navigation; // 导航用
    public boolean isLeaf() {
        return navigation ? 0 == children.size() : leaf;
    }

    @Transient
    private List<Permission> authPermissions = new ArrayList<>();
    
    
    @Override
    public String toString() {
        return "Menu{" +
                "id='" + id + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", lastModifiedDate=" + lastModifiedDate +
                ", lastModifiedDate0=" + lastModifiedDate0 +
                ", parent=" + parent +
                ", menuCode='" + menuCode + '\'' +
                ", menuName='" + menuName + '\'' +
                ", tip='" + tip + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", navigation=" + navigation +
                '}';
    }
}
