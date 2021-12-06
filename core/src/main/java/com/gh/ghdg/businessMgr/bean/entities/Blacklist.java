package com.gh.ghdg.businessMgr.bean.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Document(collection = "blacklist")
public class Blacklist extends BaseMongoEntity{
    private String bloggerId;
    private String bloggerName;
    private String reason;
    
    private Date until;//封禁时长，博主账户直到当前时间大于该until才能重新登陆使用
    
    public Blacklist() {
    }
    
    public Blacklist(String bloggerId, String bloggerName, String reason, Date until) {
        this.bloggerId = bloggerId;
        this.bloggerName = bloggerName;
        this.reason = reason;
        this.until = until;
    }
    
    public String getBloggerId() {
        return bloggerId;
    }
    
    public void setBloggerId(String bloggerId) {
        this.bloggerId = bloggerId;
    }
    
    public String getBloggerName() {
        return bloggerName;
    }
    
    public void setBloggerName(String bloggerName) {
        this.bloggerName = bloggerName;
    }
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    public Date getUntil() {
        return until;
    }
    
    public void setUntil(Date until) {
        this.until = until;
    }
    
    public String getReason() {
        return reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }
    
    @Override
    public String toString() {
        return "Blacklist{" +
                "bloggerId='" + bloggerId + '\'' +
                ", bloggerName='" + bloggerName + '\'' +
                ", reason='" + reason + '\'' +
                ", until=" + until +
                '}';
    }
}
