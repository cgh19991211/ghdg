package com.gh.ghdg.sysMgr.bean.entities.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gh.ghdg.sysMgr.bean.TreeEntity;
import com.gh.ghdg.sysMgr.bean.Unique;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "sys_module")
public class Module extends TreeEntity<Module> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "parent_id")
    private Module parent;
    
    @NotBlank
    @Length(max = 50,message = "模块名字不超过50字")
    @Unique(name = "模块名称")
    private String moduleName;
    
    @NotBlank
    @Length(max = 50,message = "模块编码不超过50位")
    @Unique(name = "模块编码")
    private String moduleCode;

    @Length(max = 50,message = "备注不超过50字")
    private String remark;
}
