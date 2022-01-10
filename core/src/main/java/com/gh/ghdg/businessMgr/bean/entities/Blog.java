package com.gh.ghdg.businessMgr.bean.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gh.ghdg.common.enums.Status;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Document(collection = "blog")
public class Blog extends BaseMongoEntity{
    
    //博客所属博主
    private String bloggerId;
    private String bloggerName;//实际上用的是Blogger的nickname
    private String bloggerAvatar;
    //博客内容
    private String title;
    private String content;
    private Integer likeNums = 0;
    private Integer badNums = 0;
    private Integer viewNums = 0;
    private Integer favoriteNum=0;
    private String icon;
    //分类
    private Category category;
    //博客标签
    private List<Label> labels;
    //状态
    private Status status = Status.审核中;
    private Boolean isPrivate = false;
    private Date createdDate;
    private Date lastModifiedDate;
    
    
    public Blog() {
    }
    
    public Blog(String bloggerId, String bloggerName, String bloggerAvatar, String title, String content, Integer likeNums, Integer badNums, Integer viewNums, Integer favoriteNum, String icon, Category category, List<Label> labels, Status status, Boolean isPrivate, Date createdDate, Date lastModifiedDate) {
        this.bloggerId = bloggerId;
        this.bloggerName = bloggerName;
        this.bloggerAvatar = bloggerAvatar;
        this.title = title;
        this.content = content;
        this.likeNums = likeNums;
        this.badNums = badNums;
        this.viewNums = viewNums;
        this.favoriteNum = favoriteNum;
        this.icon = icon;
        this.category = category;
        this.labels = labels;
        this.status = status;
        this.isPrivate = isPrivate;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
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
    
    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    public Date getLastModifiedDate() {
        return lastModifiedDate;
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
    
    public Integer getLikeNums() {
        return likeNums;
    }
    
    public void setLikeNums(Integer likeNums) {
        this.likeNums = likeNums;
    }
    
    public Integer getBadNums() {
        return badNums;
    }
    
    public void setBadNums(Integer badNums) {
        this.badNums = badNums;
    }
    
    public Integer getViewNums() {
        return viewNums;
    }
    
    public void setViewNums(Integer viewNums) {
        this.viewNums = viewNums;
    }
    
    public Integer getFavoriteNum() {
        return favoriteNum;
    }
    
    public void setFavoriteNum(Integer favoriteNum) {
        this.favoriteNum = favoriteNum;
    }
    
    public String getIcon() {
        return icon;
    }
    
    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    public Category getCategory() {
        return category;
    }
    
    public void setCategory(Category category) {
        this.category = category;
    }
    
    public List<Label> getLabels() {
        return labels;
    }
    
    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    public Boolean getPrivate() {
        return isPrivate;
    }
    
    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }
    
    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
    
    @Override
    public String toString() {
        return "Blog{" +
                "bloggerId='" + bloggerId + '\'' +
                ", bloggerName='" + bloggerName + '\'' +
                ", bloggerAvatar='" + bloggerAvatar + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", likeNums=" + likeNums +
                ", badNums=" + badNums +
                ", viewNums=" + viewNums +
                ", favoriteNum=" + favoriteNum +
                ", icon='" + icon + '\'' +
                ", category=" + category +
                ", labels=" + labels +
                ", status=" + status +
                ", isPrivate=" + isPrivate +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
    
    @Override
    public boolean isNew(){
        return createdDate==null;
    }
}
