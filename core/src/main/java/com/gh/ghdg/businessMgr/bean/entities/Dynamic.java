package com.gh.ghdg.businessMgr.bean.entities;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 点赞、收藏、回复 都是动态，在个人中心单独一个“个人动态”列表显示
 */
@Document
public class Dynamic extends BaseMongoEntity{
    @Indexed
    private String belongId;//动态所属的博主
    private String action;//like、reply
    
    private String bloggerId;//动态的作者id
    private String bloggerName;
    private String bloggerAvatar;
    
    private String title;//动态的title
    private String content;//动态的内容：概要
    @CreatedDate
    private Date createdDate;
    private String type;//比如点赞，该动态点赞的是博客还是回复 -- blog,reply
    
    public Dynamic() {
    }
    
    public Dynamic(String belongId, String action, String bloggerId, String bloggerName, String bloggerAvatar, String title, String content, Date createdDate, String type) {
        this.belongId = belongId;
        this.action = action;
        this.bloggerId = bloggerId;
        this.bloggerName = bloggerName;
        this.bloggerAvatar = bloggerAvatar;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.type = type;
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
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    @Override
    public String toString() {
        return "Dynamic{" +
                "belongId='" + belongId + '\'' +
                ", action='" + action + '\'' +
                ", bloggerId='" + bloggerId + '\'' +
                ", bloggerName='" + bloggerName + '\'' +
                ", bloggerAvatar='" + bloggerAvatar + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createdDate=" + createdDate +
                ", type='" + type + '\'' +
                '}';
    }
}
