package com.gh.ghdg.businessMgr.bean.dto;

import com.gh.ghdg.common.enums.Status;

public class FollowBlogger {
    private String id;
    private String name;
    private Status status;
    
    public FollowBlogger() {
    }
    
    public FollowBlogger(String id, String name, Status status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "FollowBlogger{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}
