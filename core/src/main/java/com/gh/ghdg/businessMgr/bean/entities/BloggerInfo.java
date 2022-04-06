package com.gh.ghdg.businessMgr.bean.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gh.ghdg.businessMgr.bean.entities.sub.Idol;
import com.gh.ghdg.businessMgr.bean.entities.sub.FavoriteBlog;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import com.gh.ghdg.businessMgr.bean.entities.sub.Address;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Document(collection = "blogger_info")
public class BloggerInfo extends BaseMongoEntity{
    @Indexed
    @NotBlank
    private String bloggerId;//外键，指向Blogger的_id字段
    @NotBlank
    private String bloggerName = "unknown";//
    @NotBlank
    private String nickname = "unknown";//弃用
    private String signature = "unknown";//个性签名
    private Address address;
    private List<Category> followedCategories;//关注的分类
    private List<FavoriteBlog> favoriteBlogs;//收藏的博客
    private List<Idol> idols; //关注的博主
    private List<Idol> fans;
    private Date registrationDate;
    private String phone = "unknown";
    private String email = "unknown";
    private String avatar;//需要一个默认头像
    private Integer blogNums = 0;//发表的博客数
    private Integer likeNums = 0;//点赞数
    
    public BloggerInfo() {
    }
    
    public BloggerInfo(String bloggerId, String bloggerName, String nickname, String signature, Address address, List<Category> followedCategories, List<FavoriteBlog> favoriteBlogs, List<Idol> idols, List<Idol> fans, Date registrationDate, String phone, String email, String avatar, Integer blogNums, Integer likeNums) {
        this.bloggerId = bloggerId;
        this.bloggerName = bloggerName;
        this.nickname = nickname;
        this.signature = signature;
        this.address = address;
        this.followedCategories = followedCategories;
        this.favoriteBlogs = favoriteBlogs;
        this.idols = idols;
        this.fans = fans;
        this.registrationDate = registrationDate;
        this.phone = phone;
        this.email = email;
        this.avatar = avatar;
        this.blogNums = blogNums;
        this.likeNums = likeNums;
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
    
    public List<Category> getFollowedCategories() {
        return followedCategories;
    }
    
    public void setFollowedCategories(List<Category> followedCategories) {
        this.followedCategories = followedCategories;
    }
    
    public List<FavoriteBlog> getFavoriteBlogs() {
        return favoriteBlogs;
    }
    
    public void setFavoriteBlogs(List<FavoriteBlog> favoriteBlogs) {
        this.favoriteBlogs = favoriteBlogs;
    }
    
    public List<Idol> getIdols() {
        return idols;
    }
    
    public void setIdols(List<Idol> idols) {
        this.idols = idols;
    }
    
    public List<Idol> getFans() {
        return fans;
    }
    
    public void setFans(List<Idol> fans) {
        this.fans = fans;
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
                ", idols=" + idols +
                ", fans=" + fans +
                ", registrationDate=" + registrationDate +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", blogNums=" + blogNums +
                ", likeNums=" + likeNums +
                '}';
    }
    
    @Override
    public boolean isNew(){
        return registrationDate==null;
    }
}
