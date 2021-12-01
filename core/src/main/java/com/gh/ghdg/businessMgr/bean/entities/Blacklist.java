package com.gh.ghdg.businessMgr.bean.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "blacklist")
public class Blacklist extends BaseMongoEntity{
    private String blogger_id;
    private String blogger_name;
    private Date until;//封禁时长，博主账户直到当前时间大于该until才能重新登陆使用
    
    public Blacklist() {
    }
    
    public Blacklist(String blogger_id, String blogger_name, Date until) {
        this.blogger_id = blogger_id;
        this.blogger_name = blogger_name;
        this.until = until;
    }
    
    public String getBlogger_id() {
        return blogger_id;
    }
    
    public void setBlogger_id(String blogger_id) {
        this.blogger_id = blogger_id;
    }
    
    public String getBlogger_name() {
        return blogger_name;
    }
    
    public void setBlogger_name(String blogger_name) {
        this.blogger_name = blogger_name;
    }
    
    public Date getUntil() {
        return until;
    }
    
    public void setUntil(Date until) {
        this.until = until;
    }
    
    @Override
    public String toString() {
        return "Blacklist{" +
                "blogger_id='" + blogger_id + '\'' +
                ", blogger_name='" + blogger_name + '\'' +
                ", until=" + until +
                '}';
    }
}
