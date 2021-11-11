package com.gh.ghdg.sysMgr.bean.entities.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gh.ghdg.sysMgr.bean.TreeEntity;
import com.gh.ghdg.sysMgr.bean.Unique;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "sys_module")
public class Module extends TreeEntity<Module> {

    private Module parent;
    
    private String moduleName;
    
    private String moduleCode;

    private String remark;
    
    public Module() {
    }
    
    public Module(Module parent, String moduleName, String moduleCode, String remark) {
        this.parent = parent;
        this.moduleName = moduleName;
        this.moduleCode = moduleCode;
        this.remark = remark;
    }
    
    @Override
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    public Module getParent() {
        return parent;
    }
    
    @Override
    @JoinColumn(name = "parent_id",referencedColumnName = "id")
    public void setParent(Module parent) {
        this.parent = parent;
    }
    
    @NotBlank
    @Length(max = 50,message = "模块名字不超过50字")
    @Unique(name = "模块名称")
    public String getModuleName() {
        return moduleName;
    }
    
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
    
    @NotBlank
    @Length(max = 50,message = "模块编码不超过50位")
    @Unique(name = "模块编码")
    public String getModuleCode() {
        return moduleCode;
    }
    
    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }
    
    @Length(max = 50,message = "备注不超过50字")
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    @Override
    public String toString() {
        return "Module{" +
                "id='" + id + '\'' +
                ", createdBy=" + createdBy +
                ", createdDate=" + createdDate +
                ", lastModifiedBy=" + lastModifiedBy +
                ", lastModifiedDate=" + lastModifiedDate +
                ", lastModifiedDate0=" + lastModifiedDate0 +
                ", displaySeq=" + displaySeq +
                ", children=" + children +
                ", leaf=" + leaf +
                ", checked=" + checked +
                ", expanded=" + expanded +
                ", ignoreChildren=" + ignoreChildren +
                ", parent=" + parent +
                ", moduleName='" + moduleName + '\'' +
                ", moduleCode='" + moduleCode + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
