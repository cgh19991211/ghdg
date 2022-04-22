package com.gh.ghdg.businessMgr.bean.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gh.ghdg.businessMgr.bean.entities.sub.ThumbsUp;
import com.gh.ghdg.common.enums.Status;
import org.apache.commons.collections.ArrayStack;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Document(collection = "blog")
public class Blog extends BaseMongoEntity{
    
    //博客所属博主
    @Indexed
    private String bloggerId;
    @Indexed
    private String bloggerName;
    private String bloggerAvatar;
    //博客内容
    private String title;
    private String content;
    private String summarize;
    private List<ThumbsUp> likes = new ArrayList<>();//点赞该博客的人
    private Integer viewNums = 0;
    private List<ThumbsUp> storeUp = new ArrayList<>();
    private String icon;
    //分类
    private Category category;
    //博客标签
    private List<Label> labels = new ArrayList<>();
    //状态
    private Status status = Status.审核中;
    private List<String> reason = new ArrayList<>();
    private Boolean isPrivate = false;
    private Date createdDate;
    private Date lastModifiedDate;
    
    
    public Blog() {
    }
    
    public Blog(String bloggerId, String bloggerName, String bloggerAvatar, String title, String content, String summarize, List<ThumbsUp> likes, Integer viewNums, List<ThumbsUp> storeUp, String icon, Category category, List<Label> labels, Status status, List<String> reason, Boolean isPrivate, Date createdDate, Date lastModifiedDate) {
        this.bloggerId = bloggerId;
        this.bloggerName = bloggerName;
        this.bloggerAvatar = bloggerAvatar;
        this.title = title;
        this.content = content;
        this.summarize = summarize;
        this.likes = likes;
        this.viewNums = viewNums;
        this.storeUp = storeUp;
        this.icon = icon;
        this.category = category;
        this.labels = labels;
        this.status = status;
        this.reason = reason;
        this.isPrivate = isPrivate;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
    
    public List<ThumbsUp> getStoreUp() {
        return storeUp;
    }
    
    public void setStoreUp(List<ThumbsUp> storeUp) {
        this.storeUp = storeUp;
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
    
    public List<ThumbsUp> getLikes() {
        return likes;
    }
    
    public void setLikes(List<ThumbsUp> likes) {
        this.likes = likes;
    }
   
    public Integer getViewNums() {
        return viewNums;
    }
    
    public void setViewNums(Integer viewNums) {
        this.viewNums = viewNums;
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
    
    public void addLabel(Label l){
        if(this.labels==null)
            this.labels = new ArrayList<>();
        this.labels.add(l);
    }
    
    public String getSummarize() {
        return summarize;
    }
    
    public void setSummarize(String summarize) {
        this.summarize = summarize;
    }
    
    public List<String> getReason() {
        return reason;
    }
    
    public void setReason(List<String> reason) {
        this.reason = reason;
    }
    
    @Override
    public String toString() {
        return "Blog{" +
                "_id='" + _id + '\'' +
                ", bloggerId='" + bloggerId + '\'' +
                ", bloggerName='" + bloggerName + '\'' +
                ", bloggerAvatar='" + bloggerAvatar + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", summarize='" + summarize + '\'' +
                ", likes=" + likes +
                ", viewNums=" + viewNums +
                ", storeUp=" + storeUp +
                ", icon='" + icon + '\'' +
                ", category=" + category +
                ", labels=" + labels +
                ", status=" + status +
                ", reason=" + reason +
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
