package com.gh.ghdg.oprMgr.entities.notice;

import com.gh.ghdg.sysMgr.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Data
@Entity(name = "sys_notice_sender")
@Table(appliesTo = "sys_notice_sender",comment = "通知发送者")
public class NoticeSender extends BaseEntity {
    @Column(name="name",columnDefinition = "VARCHAR(64) COMMENT '名称'")
    @NotBlank(message = "发送方名称不能为空")
    private String name;
    @Column(name="class_name",columnDefinition = "VARCHAR(64) COMMENT '发送类'")
    @NotBlank(message = "发送类不能为空")
    private String className;
    @Column(name="tpl_code",columnDefinition = "VARCHAR(64) COMMENT '模板编号'")
    private String tplCode;
    
}
