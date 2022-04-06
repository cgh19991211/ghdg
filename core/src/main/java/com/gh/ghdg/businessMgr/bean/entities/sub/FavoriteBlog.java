package com.gh.ghdg.businessMgr.bean.entities.sub;

import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

public class FavoriteBlog {
    private String blogId;
    private String bloggerId;
    private String bloggerName;
    private String bloggerAvatar;
    private String title;
    @CreatedDate
    private Date favoriteDate;
    private String content; //概要
    
    public FavoriteBlog() {
    }
    
    public FavoriteBlog(String blogId, String bloggerId, String bloggerName, String bloggerAvatar, String title, Date favoriteDate, String content) {
        this.blogId = blogId;
        this.bloggerId = bloggerId;
        this.bloggerName = bloggerName;
        this.bloggerAvatar = bloggerAvatar;
        this.title = title;
        this.favoriteDate = favoriteDate;
        this.content = content;
    }
    
    @Override
    public String toString() {
        return "FavoriteBlog{" +
                "blogId='" + blogId + '\'' +
                ", bloggerId='" + bloggerId + '\'' +
                ", bloggerName='" + bloggerName + '\'' +
                ", bloggerAvatar='" + bloggerAvatar + '\'' +
                ", title='" + title + '\'' +
                ", favoriteDate=" + favoriteDate +
                ", content='" + content + '\'' +
                '}';
    }
    
    public String getBlogId() {
        return blogId;
    }
    
    public void setBlogId(String blogId) {
        this.blogId = blogId;
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
    
    public Date getFavoriteDate() {
        return favoriteDate;
    }
    
    public void setFavoriteDate(Date favoriteDate) {
        this.favoriteDate = favoriteDate;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
}
