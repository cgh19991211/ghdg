package com.gh.ghdg.businessMgr.bean.vo;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gh.ghdg.businessMgr.bean.entities.Blog;
import com.gh.ghdg.businessMgr.utils.MDToText;
import org.pegdown.PegDownProcessor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class TopicVo {
    String action;
    Date date;
    String title;
    String bloggerAvatar;
    String bloggerName;
    String content;
    
    public TopicVo() {
    }
    
    public TopicVo(String action, Date date, String title, String bloggerAvatar, String bloggerName, String content) {
        this.action = action;
        this.date = date;
        this.title = title;
        this.bloggerAvatar = bloggerAvatar;
        this.bloggerName = bloggerName;
        this.content = content;
    }
    
    public String getAction() {
        return action;
    }
    
    public void setAction(String action) {
        this.action = action;
    }
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
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
                ", date=" + date +
                ", title='" + title + '\'' +
                ", bloggerAvatar='" + bloggerAvatar + '\'' +
                ", bloggerName='" + bloggerName + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
    
    public static TopicVo BlogToTopicVo(Blog blog){
        TopicVo topicVo = new TopicVo();
        topicVo.setAction("发表了博客");
        topicVo.setBloggerName(blog.getBloggerName());
        topicVo.setBloggerAvatar(blog.getBloggerAvatar());
        topicVo.setTitle(blog.getTitle());
        topicVo.setDate(blog.getCreatedDate());
        String content = blog.getContent();
        if(content!=null){
            PegDownProcessor pdp = new PegDownProcessor(Integer.MAX_VALUE);
            String htmlContent = pdp.markdownToHtml(content);
            int length = htmlContent.length();
            if(length>300)
                topicVo.setContent(htmlContent.substring(0,300)+"......");
            else
                topicVo.setContent(htmlContent);
//            String str = MDToText.mdToText(content);
//            int length = str.length();
//            if(length>100)
//                topicVo.setContent(str.substring(0,100)+"......");
//            else
//                topicVo.setContent(str);
        }
        
        return topicVo;
    }
}
