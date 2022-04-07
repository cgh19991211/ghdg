package com.gh.ghdg.businessMgr.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gh.ghdg.businessMgr.bean.entities.Blog;
import com.gh.ghdg.businessMgr.bean.entities.Comment;
import org.pegdown.PegDownProcessor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class TopicVo {
    String action;
    Date createdDate;
    String title;
    String bloggerAvatar;
    String bloggerName;
    String content;
    
    String blogId;
    
    public TopicVo() {
    }
    
    public TopicVo(String action, Date createdDate, String title, String bloggerAvatar, String bloggerName, String content, String blogId) {
        this.action = action;
        this.createdDate = createdDate;
        this.title = title;
        this.bloggerAvatar = bloggerAvatar;
        this.bloggerName = bloggerName;
        this.content = content;
        this.blogId = blogId;
    }
    
    public String getBlogId() {
        return blogId;
    }
    
    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }
    
    public String getAction() {
        return action;
    }
    
    public void setAction(String action) {
        this.action = action;
    }
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    public Date getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getBloggerName() {
        return bloggerName;
    }
    
    public void setBloggerName(String bloggerName) {
        this.bloggerName = bloggerName;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getBloggerAvatar() {
        return bloggerAvatar;
    }
    
    public void setBloggerAvatar(String bloggerAvatar) {
        this.bloggerAvatar = bloggerAvatar;
    }
    
    @Override
    public String toString() {
        return "TopicVo{" +
                "action='" + action + '\'' +
                ", createdDate=" + createdDate +
                ", title='" + title + '\'' +
                ", bloggerAvatar='" + bloggerAvatar + '\'' +
                ", bloggerName='" + bloggerName + '\'' +
                ", content='" + content + '\'' +
                ", blogId='" + blogId + '\'' +
                '}';
    }
    
    public static TopicVo blogToTopicVo(Blog blog){
        TopicVo topicVo = new TopicVo();
        topicVo.setAction("发表了文章");
        topicVo.setBloggerName(blog.getBloggerName());
        topicVo.setBloggerAvatar(blog.getBloggerAvatar());
        topicVo.setTitle(blog.getTitle());
        topicVo.setCreatedDate(blog.getCreatedDate());
        topicVo.setBlogId(blog.getId());
        String content = blog.getContent();
        if(content!=null){
            PegDownProcessor pdp = new PegDownProcessor(Integer.MAX_VALUE);
            String htmlContent = pdp.markdownToHtml(content);
            int length = htmlContent.length();
            if(length>300)
                topicVo.setContent(htmlContent.substring(0,300)+"......");
            else
                topicVo.setContent(htmlContent);
        }
        return topicVo;
    }
    
    public static TopicVo commentToTopicVo(Comment comment,Blog blog){
        TopicVo topicVo = new TopicVo();
        topicVo.setAction("发表了回复");
        topicVo.setCreatedDate(comment.getCreatedDate());
        topicVo.setTitle(blog.getTitle());//回复的文章的title
        topicVo.setBloggerName(blog.getBloggerName());
        topicVo.setBlogId(blog.getId());
        topicVo.setBloggerAvatar(blog.getBloggerAvatar());
        String content = comment.getContent();
    
        if(content!=null){
            PegDownProcessor pdp = new PegDownProcessor(Integer.MAX_VALUE);
            String htmlContent = pdp.markdownToHtml(content);
            int length = htmlContent.length();
            if(length>300)
                topicVo.setContent(htmlContent.substring(0,300)+"......");
            else
                topicVo.setContent(htmlContent);
        }
        return topicVo;
    }
}
