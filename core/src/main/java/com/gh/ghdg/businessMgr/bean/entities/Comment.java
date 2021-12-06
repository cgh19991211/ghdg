package com.gh.ghdg.businessMgr.bean.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.util.Date;
import java.util.Set;

@Document(collection = "comment")
public class Comment extends BaseMongoEntity{
    private String blogId;//评论所属文章
    private String bloggerId;//评论人
    private String bloggerName;
    private String bloggerAvatar;
    private String content;
    private Date createdDate;// = Instant.now()
    private String status = "生效";
    private Set<Comment> responses;
    private Integer level = 1;//1级评论，二级评论，三级评论及往后以@的形式显示在二级评论下
    
    public Comment() {
    }
    
    public Comment(String blogId, String bloggerId, String bloggerName, String bloggerAvatar, String content, Date createdDate, String status, Set<Comment> responses, Integer level) {
        this.blogId = blogId;
        this.bloggerId = bloggerId;
        this.bloggerName = bloggerName;
        this.bloggerAvatar = bloggerAvatar;
        this.content = content;
        this.createdDate = createdDate;
        this.status = status;
        this.responses = responses;
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
                "blogId='" + blogId + '\'' +
                ", bloggerId='" + bloggerId + '\'' +
                ", bloggerName='" + bloggerName + '\'' +
                ", bloggerAvatar='" + bloggerAvatar + '\'' +
                ", content='" + content + '\'' +
                ", createdDate=" + createdDate +
                ", status='" + status + '\'' +
                ", responses=" + responses +
                ", level=" + level +
                '}';
    }
    
    @Override
    public boolean isNew() {
        return createdDate == null;
    }
}
