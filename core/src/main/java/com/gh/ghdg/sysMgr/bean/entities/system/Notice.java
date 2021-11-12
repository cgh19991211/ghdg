package com.gh.ghdg.sysMgr.bean.entities.system;

import com.gh.ghdg.sysMgr.BaseEntity;
import org.hibernate.annotations.Table;

import javax.persistence.Entity;

@Entity(name = "sys_notice")
@Table(appliesTo = "sys_notice",comment = "后台系统通知")
public class Notice extends BaseEntity {
    private String title;
    private String content;
    private Integer type;
    
    public Notice() {
    }
    
    public Notice(String title, String content, Integer type) {
        this.title = title;
        this.content = content;
        this.type = type;
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
    
    public Integer getType() {
        return type;
    }
    
    public void setType(Integer type) {
        this.type = type;
    }
    
    @Override
    public String toString() {
        return "SysNotice{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", type=" + type +
                '}';
    }
}
