package com.gh.ghdg.oprMgr.entities.notice;

import com.gh.ghdg.sysMgr.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.Table;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity(name = "sys_notice_template")
@Table(appliesTo = "sys_notice_template",comment = "通知模板")
public class NoticeTemplate extends BaseEntity {
    @Column(name="code",columnDefinition = "VARCHAR(32) COMMENT '编号'")
    @NotBlank(message = "编号不能为空")
    private String code;
    @NotBlank(message = "标题不能为空")
    @Column(name="title",columnDefinition = "VARCHAR(64) COMMENT '标题'")
    private String title;
    @NotBlank(message = "内容并能为空")
    @Column(name="content",columnDefinition = "TEXT COMMENT '内容'")
    private String content;
    @Column(name="notice_sender_id",columnDefinition = "VARCHAR(32) COMMENT '发送者id'")
    @NotNull(message = "发送器不能为空")
    private String noticeSenderId;
    @Column(name="type",columnDefinition = "VARCHAR(30) COMMENT '消息类型,0:短信,1:邮件'")
    private Integer type;
    @JoinColumn(name="notice_sender_id", referencedColumnName = "id",  columnDefinition = "VARCHAR(32) comment '发送者id'", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private NoticeSender noticeSender;
    
    public NoticeTemplate() {
    }
    
    public NoticeTemplate(String code, String title, String content, String noticeSenderId, Integer type, NoticeSender noticeSender) {
        this.code = code;
        this.title = title;
        this.content = content;
        this.noticeSenderId = noticeSenderId;
        this.type = type;
        this.noticeSender = noticeSender;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getNoticeSenderId() {
        return noticeSenderId;
    }
    
    public void setNoticeSenderId(String noticeSenderId) {
        this.noticeSenderId = noticeSenderId;
    }
    
    public Integer getType() {
        return type;
    }
    
    public void setType(Integer type) {
        this.type = type;
    }
    
    public NoticeSender getNoticeSender() {
        return noticeSender;
    }
    
    public void setNoticeSender(NoticeSender noticeSender) {
        this.noticeSender = noticeSender;
    }
    
    @Override
    public String toString() {
        return "NoticeTemplate{" +
                "code='" + code + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", noticeSenderId='" + noticeSenderId + '\'' +
                ", type=" + type +
                ", noticeSender=" + noticeSender +
                ", id='" + id + '\'' +
                '}';
    }
}
