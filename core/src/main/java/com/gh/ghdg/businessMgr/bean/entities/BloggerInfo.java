package com.gh.ghdg.businessMgr.bean.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import com.gh.ghdg.businessMgr.bean.entities.sub.Address;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;

@Document(collection = "blogger_info")
public class BloggerInfo extends BaseMongoEntity{
    private String bloggerId;//外键，指向Blogger的_id字段
    private String bloggerName = "unknown";
    private String nickname = "unknown";
    private String signature = "unknown";//个性签名
    private Address address;
    private Set<Category> followedCategories;//关注的分类
    private Set<Blog> favoriteBlogs;//收藏的博客
    private Set<Blogger> followedBloggers;//关注的博主
    private Date registrationDate;
    private String phone = "unknown";
    private String email = "unknown";
    private String avatar;//需要一个默认头像
    private Integer blogNums = 0;//发表的博客数
    private Integer fanNums = 0;//粉丝(关注的人)数
    private Integer likeNums = 0;//点赞数
    
    public BloggerInfo() {
    }
    
    public BloggerInfo(String bloggerId, String bloggerName, String nickname, String signature, Address address, Set<Category> followedCategories, Set<Blog> favoriteBlogs, Set<Blogger> followedBloggers, Date registrationDate, String phone, String email, String avatar, Integer blogNums, Integer fanNums, Integer likeNums) {
        this.bloggerId = bloggerId;
        this.bloggerName = bloggerName;
        this.nickname = nickname;
        this.signature = signature;
        this.address = address;
        this.followedCategories = followedCategories;
        this.favoriteBlogs = favoriteBlogs;
        this.followedBloggers = followedBloggers;
        this.registrationDate = registrationDate;
        this.phone = phone;
        this.email = email;
        this.avatar = avatar;
        this.blogNums = blogNums;
        this.fanNums = fanNums;
        this.likeNums = likeNums;
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
    
    public String getNickname() {
        return nickname;
    }
    
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    
    public String getSignature() {
        return signature;
    }
    
    public void setSignature(String signature) {
        this.signature = signature;
    }
    
    public Address getAddress() {
        return address;
    }
    
    public void setAddress(Address address) {
        this.address = address;
    }
    
    public Set<Category> getFollowedCategories() {
        return followedCategories;
    }
    
    public void setFollowedCategories(Set<Category> followedCategories) {
        this.followedCategories = followedCategories;
    }
    
    public Set<Blog> getFavoriteBlogs() {
        return favoriteBlogs;
    }
    
    public void setFavoriteBlogs(Set<Blog> favoriteBlogs) {
        this.favoriteBlogs = favoriteBlogs;
    }
    
    public Set<Blogger> getFollowedBloggers() {
        return followedBloggers;
    }
    
    public void setFollowedBloggers(Set<Blogger> followedBloggers) {
        this.followedBloggers = followedBloggers;
    }
    
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    public Date getRegistrationDate() {
        return registrationDate;
    }
    
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getAvatar() {
        return avatar;
    }
    
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    
    public Integer getBlogNums() {
        return blogNums;
    }
    
    public void setBlogNums(Integer blogNums) {
        this.blogNums = blogNums;
    }
    
    public Integer getFanNums() {
        return fanNums;
    }
    
    public void setFanNums(Integer fanNums) {
        this.fanNums = fanNums;
    }
    
    public Integer getLikeNums() {
        return likeNums;
    }
    
    public void setLikeNums(Integer likeNums) {
        this.likeNums = likeNums;
    }
    
    @Override
    public String toString() {
        return "BloggerInfo{" +
                "bloggerId='" + bloggerId + '\'' +
                ", bloggerName='" + bloggerName + '\'' +
                ", nickname='" + nickname + '\'' +
                ", signature='" + signature + '\'' +
                ", address=" + address +
                ", followedCategories=" + followedCategories +
                ", favoriteBlogs=" + favoriteBlogs +
                ", followedBloggers=" + followedBloggers +
                ", registrationDate=" + registrationDate +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", blogNums=" + blogNums +
                ", fanNums=" + fanNums +
                ", likeNums=" + likeNums +
                '}';
    }
    
    @Override
    public boolean isNew(){
        return registrationDate==null;
    }
}
