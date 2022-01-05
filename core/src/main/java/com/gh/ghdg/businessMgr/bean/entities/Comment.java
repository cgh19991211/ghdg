package com.gh.ghdg.businessMgr.bean.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gh.ghdg.common.enums.Status;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Convert;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "comment")
public class Comment extends BaseMongoEntity{
    private String blogId;//评论所属文章
    private String bloggerId;//评论人id
    private String bloggerName;//评论人名字
    private String bloggerAvatar;
    private String content;
    private Date createdDate;// = Instant.now()
    private Status status = Status.审核中;//false代表禁用，不可见
    private HashSet<Comment> responses;
    private Integer likeNum = 0;
    private Integer level = 1;//1级评论，二级评论，三级评论及往后以@的形式显示在二级评论下
    
    public Comment() {
    }
    
    public Comment(String blogId, String bloggerId, String bloggerName, String bloggerAvatar, String content, Date createdDate, Status status, HashSet<Comment> responses, Integer likeNum, Integer level) {
        this.blogId = blogId;
        this.bloggerId = bloggerId;
        this.bloggerName = bloggerName;
        this.bloggerAvatar = bloggerAvatar;
        this.content = content;
        this.createdDate = createdDate;
        this.status = status;
        this.responses = responses;
        this.likeNum = likeNum;
        this.level = level;
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
    
    @Convert(converter = Status.Converter.class)
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    public HashSet<Comment> getResponses() {
        return responses;
    }
    
    public void setResponses(HashSet<Comment> responses) {
        this.responses = responses;
    }
    
    public Integer getLevel() {
        return level;
    }
    
    public void setLevel(Integer level) {
        this.level = level;
    }
    
    public Integer getLikeNum() {
        return likeNum;
    }
    
    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }
    
    @Override
    public String toString() {
        return "Comment{" +
                "blogId='" + blogId + '\'' +
                ", bloggerId='" + bloggerId + '\'' +
                ", bloggerName='" + bloggerName + '\'' +
                ", bloggerAvatar='" + bloggerAvatar + '\'' +
                ", content='" + content + '\'' +
                ", createdDate=" + createdDate +
                ", status=" + status +
                ", responses=" + responses +
                ", likeNum=" + likeNum +
                ", level=" + level +
                '}';
    }
    
    @Override
    public boolean isNew() {
        return createdDate == null;
    }
}
