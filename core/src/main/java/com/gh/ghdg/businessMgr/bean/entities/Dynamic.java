package com.gh.ghdg.businessMgr.bean.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 点赞、收藏、回复 都是动态，在个人中心单独一个“个人动态”列表显示
 */
@Document
public class Dynamic extends BaseMongoEntity{
    @Indexed
    private String belongId;//动态所属的博主
    
    /**
     * 被点赞(..)的内容的作者的信息
     */
    private String bloggerId;
    private String bloggerName;
    private String bloggerAvatar;
    
    /**
     * 动态的内容
     */
    private String blogId;
    private String action;//like、reply
    private String title;//动态的title
    private String content;//动态的内容：概要
    private Date createdDate;
    
    public Dynamic() {
    }
    
    public Dynamic(String belongId, String bloggerId, String bloggerName, String bloggerAvatar, String blogId, String action, String title, String content, Date createdDate) {
        this.belongId = belongId;
        this.bloggerId = bloggerId;
        this.bloggerName = bloggerName;
        this.bloggerAvatar = bloggerAvatar;
        this.blogId = blogId;
        this.action = action;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
    }
    
    public String getBlogId() {
        return blogId;
    }
    
    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }
    
    public String getBelongId() {
        return belongId;
    }
    
    public void setBelongId(String belongId) {
        this.belongId = belongId;
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
    
    public String getBloggerAvatar() {
        return bloggerAvatar;
    }
    
    public void setBloggerAvatar(String bloggerAvatar) {
        this.bloggerAvatar = bloggerAvatar;
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
    
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    public Date getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    
    public String getAction() {
        return action;
    }
    
    public void setAction(String action) {
        this.action = action;
    }
    
    @Override
    public String toString() {
        return "Dynamic{" +
                "_id='" + _id + '\'' +
                ", belongId='" + belongId + '\'' +
                ", bloggerId='" + bloggerId + '\'' +
                ", bloggerName='" + bloggerName + '\'' +
                ", bloggerAvatar='" + bloggerAvatar + '\'' +
                ", blogId='" + blogId + '\'' +
                ", action='" + action + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
