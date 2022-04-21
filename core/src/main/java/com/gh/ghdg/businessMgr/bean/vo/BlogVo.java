package com.gh.ghdg.businessMgr.bean.vo;


import java.util.Arrays;

public class BlogVo {
    String blogId;
    String title;
    String[] labelIds;
    String content;
    String summarize = "摘要内容为空哦";
    
    public BlogVo() {
    }
    
    public BlogVo(String blogId, String title, String[] labelIds, String content) {
        this.blogId = blogId;
        this.title = title;
        this.labelIds = labelIds;
        this.content = content;
    }
    
    public BlogVo(String blogId, String title, String[] labelIds, String content, String summarize) {
        this.blogId = blogId;
        this.title = title;
        this.labelIds = labelIds;
        this.content = content;
        this.summarize = summarize;
    }
    
    public String getBlogId() {
        return blogId;
    }
    
    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String[] getLabelIds() {
        return labelIds;
    }
    
    public void setLabelIds(String[] labelIds) {
        this.labelIds = labelIds;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getSummarize() {
        return summarize;
    }
    
    public void setSummarize(String summarize) {
        this.summarize = summarize;
    }
    
    @Override
    public String toString() {
        return "BlogVo{" +
                "blogId='" + blogId + '\'' +
                ", title='" + title + '\'' +
                ", labelIds=" + Arrays.toString(labelIds) +
                ", content='" + content + '\'' +
                ", summarize='" + summarize + '\'' +
                '}';
    }
}
