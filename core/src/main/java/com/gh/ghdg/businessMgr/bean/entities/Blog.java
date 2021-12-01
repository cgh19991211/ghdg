package com.gh.ghdg.businessMgr.bean.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Set;

@Document(collection = "article")
public class Blog extends BaseMongoEntity{
    
    //博客所属博主
    private String blogger_id;
    private String blogger_name;
    private String blogger_avatar;
    
    //博客内容
    private String title;
    private String content;
    private Integer like_nums;
    private Integer view_nums;
    
    private Set<Comment> comments;
    private Integer comment_nums;
    
    private String status;
    private Category category;
    
    private Date created_date;
    private Date last_modified_date;
    
    //博客标签
    private Set<Label> labels;
    
    public Blog() {
    }
    
    public Blog(String blogger_id, String blogger_name, String blogger_avatar, String title, String content, Integer like_nums, Integer view_nums, Set<Comment> comments, Integer comment_nums, String status, Category category, Date created_date, Date last_modified_date, Set<Label> labels) {
        this.blogger_id = blogger_id;
        this.blogger_name = blogger_name;
        this.blogger_avatar = blogger_avatar;
        this.title = title;
        this.content = content;
        this.like_nums = like_nums;
        this.view_nums = view_nums;
        this.comments = comments;
        this.comment_nums = comment_nums;
        this.status = status;
        this.category = category;
        this.created_date = created_date;
        this.last_modified_date = last_modified_date;
        this.labels = labels;
    }
    
    public String getBlogger_id() {
        return blogger_id;
    }
    
    public void setBlogger_id(String blogger_id) {
        this.blogger_id = blogger_id;
    }
    
    public String getBlogger_name() {
        return blogger_name;
    }
    
    public void setBlogger_name(String blogger_name) {
        this.blogger_name = blogger_name;
    }
    
    public String getBlogger_avatar() {
        return blogger_avatar;
    }
    
    public void setBlogger_avatar(String blogger_avatar) {
        this.blogger_avatar = blogger_avatar;
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
    
    public Integer getLike_nums() {
        return like_nums;
    }
    
    public void setLike_nums(Integer like_nums) {
        this.like_nums = like_nums;
    }
    
    public Integer getView_nums() {
        return view_nums;
    }
    
    public void setView_nums(Integer view_nums) {
        this.view_nums = view_nums;
    }
    
    public Set<Comment> getComments() {
        return comments;
    }
    
    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
    
    public Integer getComment_nums() {
        return comment_nums;
    }
    
    public void setComment_nums(Integer comment_nums) {
        this.comment_nums = comment_nums;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Category getCategory() {
        return category;
    }
    
    public void setCategory(Category category) {
        this.category = category;
    }
    
    public Date getCreated_date() {
        return created_date;
    }
    
    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }
    
    public Date getLast_modified_date() {
        return last_modified_date;
    }
    
    public void setLast_modified_date(Date last_modified_date) {
        this.last_modified_date = last_modified_date;
    }
    
    public Set<Label> getLabels() {
        return labels;
    }
    
    public void setLabels(Set<Label> labels) {
        this.labels = labels;
    }
    
    @Override
    public String toString() {
        return "Blog{" +
                "_id='" + _id + '\'' +
                ", blogger_id='" + blogger_id + '\'' +
                ", blogger_name='" + blogger_name + '\'' +
                ", blogger_avatar='" + blogger_avatar + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", like_nums=" + like_nums +
                ", view_nums=" + view_nums +
                ", comments=" + comments +
                ", comment_nums=" + comment_nums +
                ", status='" + status + '\'' +
                ", category=" + category +
                ", created_date=" + created_date +
                ", last_modified_date=" + last_modified_date +
                ", labels=" + labels +
                '}';
    }
}
