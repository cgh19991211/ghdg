package com.gh.ghdg.businessMgr.bean.entities;

import org.springframework.data.mongodb.core.mapping.Document;
import com.gh.ghdg.businessMgr.bean.entities.sub.Address;

import java.util.Date;
import java.util.Set;

@Document(collation = "blogger_info")
public class BloggerInfo extends BaseMongoEntity{
    private String blogger_id;//外键，指向Blogger的_id字段
    private String name;
    private String nickname;
    private String signature;//个性签名
    private Address address;
    private Set<Blog> favorite_blogs;//收藏的博客
    private Set<Blogger> followed_bloggers;//关注的博主
    private Date registration_date;
    private String phone;
    private String email;
    private String avatar;
    private Integer blog_nums;//发表的数
    private Integer fans_nums;//粉丝(关注的人)数
    private Integer like_nums;//点赞数
    
    public BloggerInfo() {
    }
    
    public BloggerInfo(String blogger_id, String name, String nickname, String signature, Address address, Set<Blog> favorite_blogs, Set<Blogger> followed_bloggers, Date registration_date, String phone, String email, String avatar, Integer blog_nums, Integer fans_nums, Integer like_nums) {
        this.blogger_id = blogger_id;
        this.name = name;
        this.nickname = nickname;
        this.signature = signature;
        this.address = address;
        this.favorite_blogs = favorite_blogs;
        this.followed_bloggers = followed_bloggers;
        this.registration_date = registration_date;
        this.phone = phone;
        this.email = email;
        this.avatar = avatar;
        this.blog_nums = blog_nums;
        this.fans_nums = fans_nums;
        this.like_nums = like_nums;
    }
    
    public String getBlogger_id() {
        return blogger_id;
    }
    
    public void setBlogger_id(String blogger_id) {
        this.blogger_id = blogger_id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
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
    
    public Set<Blog> getFavorite_blogs() {
        return favorite_blogs;
    }
    
    public void setFavorite_blogs(Set<Blog> favorite_blogs) {
        this.favorite_blogs = favorite_blogs;
    }
    
    public Set<Blogger> getFollowed_bloggers() {
        return followed_bloggers;
    }
    
    public void setFollowed_bloggers(Set<Blogger> followed_bloggers) {
        this.followed_bloggers = followed_bloggers;
    }
    
    public Date getRegistration_date() {
        return registration_date;
    }
    
    public void setRegistration_date(Date registration_date) {
        this.registration_date = registration_date;
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
    
    public Integer getBlog_nums() {
        return blog_nums;
    }
    
    public void setBlog_nums(Integer blog_nums) {
        this.blog_nums = blog_nums;
    }
    
    public Integer getFans_nums() {
        return fans_nums;
    }
    
    public void setFans_nums(Integer fans_nums) {
        this.fans_nums = fans_nums;
    }
    
    public Integer getLike_nums() {
        return like_nums;
    }
    
    public void setLike_nums(Integer like_nums) {
        this.like_nums = like_nums;
    }
    
    @Override
    public String toString() {
        return "BloggerInfo{" +
                "blogger_id='" + blogger_id + '\'' +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", signature='" + signature + '\'' +
                ", address=" + address +
                ", favorite_blogs=" + favorite_blogs +
                ", followed_bloggers=" + followed_bloggers +
                ", registration_date=" + registration_date +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", blog_nums=" + blog_nums +
                ", fans_nums=" + fans_nums +
                ", like_nums=" + like_nums +
                '}';
    }
}
