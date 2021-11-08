package com.gh.ghdg.sysMgr.bean.entities.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gh.ghdg.sysMgr.bean.TreeEntity;
import com.gh.ghdg.sysMgr.bean.Unique;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "sys_permission")
public class Permission extends TreeEntity<Permission> {
    
    public Permission(){
        ignoreChildren = false;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    protected Permission parent;
    
    @NotBlank(message = "操作名称不得为空")
    @Length(max = 40, message = "操作名称不得超过 40 位")
    @Unique(name = "操作名称", extraFields = "menu")
    private String permissionName;
    
    @NotBlank(message = "操作代码不得为空")
    @Length(max = 40, message = "操作代码不得超过 40 位")
    @Unique(name = "操作代码", extraFields = "menu")
    private String permissionCode;
    
    @Unique(name = "资源路径")
    private String url;
    
    @Length(max = 50, message = "备注不得超过 50 位")
    private String remark;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Menu menu;

    @JsonIgnore
    @OneToMany(mappedBy = "permission") // 级联
    private List<RoleMenuPermission> roleMenuPermissions = new ArrayList<>();
    
    
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
