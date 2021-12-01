package com.gh.ghdg.businessMgr.bean.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Set;

@Document(collection = "comment")
public class Comment extends BaseMongoEntity{
    private String blog_id;//评论所属文章
    private String blogger_id;//评论人
    private String blogger_name;
    private String blogger_avatar;
    private String content;
    private Date comment_date;
    private String status;
    private Set<Comment> responses;
    private Integer level;//1级评论，二级评论，三级评论及往后以@的形式显示在二级评论下
    
    public Comment() {
    }
    
    public Comment(String blog_id, String blogger_id, String blogger_name, String blogger_avatar, String content, Date comment_date, String status, Set<Comment> responses, Integer level) {
        this.blog_id = blog_id;
        this.blogger_id = blogger_id;
        this.blogger_name = blogger_name;
        this.blogger_avatar = blogger_avatar;
        this.content = content;
        this.comment_date = comment_date;
        this.status = status;
        this.responses = responses;
        this.level = level;
    }
    
    public String getBlog_id() {
        return blog_id;
    }
    
    public void setBlog_id(String blog_id) {
        this.blog_id = blog_id;
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
    
    public String getBlogger_avatar() {
        return blogger_avatar;
    }
    
    public void setBlogger_avatar(String blogger_avatar) {
        this.blogger_avatar = blogger_avatar;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public Date getComment_date() {
        return comment_date;
    }
    
    public void setComment_date(Date comment_date) {
        this.comment_date = comment_date;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Set<Comment> getResponses() {
        return responses;
    }
    
    public void setResponses(Set<Comment> responses) {
        this.responses = responses;
    }
    
    public Integer getLevel() {
        return level;
    }
    
    public void setLevel(Integer level) {
        this.level = level;
    }
    
    @Override
    public String toString() {
        return "Comment{" +
                "blog_id='" + blog_id + '\'' +
                ", blogger_id='" + blogger_id + '\'' +
                ", blogger_name='" + blogger_name + '\'' +
                ", blogger_avatar='" + blogger_avatar + '\'' +
                ", content='" + content + '\'' +
                ", comment_date=" + comment_date +
                ", status='" + status + '\'' +
                ", responses=" + responses +
                ", level=" + level +
                '}';
    }
}
