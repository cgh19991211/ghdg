package com.gh.ghdg.businessMgr.bean.vo;

import com.gh.ghdg.businessMgr.bean.entities.Blogger;
import com.gh.ghdg.businessMgr.bean.entities.BloggerInfo;
import com.gh.ghdg.businessMgr.bean.entities.sub.Address;
import com.gh.ghdg.common.enums.Status;

import java.util.Date;

public class BloggerManageVo {
    String bloggerId;
    String bloggerName;
    String nickname;
    String bloggerAvatar;
    String signature;
    Date registrationDate;
    String phone = "unknown";
    String email = "unknown";
    Integer blogNums = 0;//发表的博客数
    Integer likeNums = 0;//点赞数
    Address address;
    Status status;
    
    public BloggerManageVo() {
    }
    
    public BloggerManageVo(String bloggerId, String bloggerName, String nickname, String bloggerAvatar, String signature, Date registrationDate, String phone, String email, Integer blogNums, Integer likeNums, Address address, Status status) {
        this.bloggerId = bloggerId;
        this.bloggerName = bloggerName;
        this.nickname = nickname;
        this.bloggerAvatar = bloggerAvatar;
        this.signature = signature;
        this.registrationDate = registrationDate;
        this.phone = phone;
        this.email = email;
        this.blogNums = blogNums;
        this.likeNums = likeNums;
        this.address = address;
        this.status = status;
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
    
    public String getSignature() {
        return signature;
    }
    
    public void setSignature(String signature) {
        this.signature = signature;
    }
    
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
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    public String getNickname() {
        return nickname;
    }
    
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    
    public Address getAddress() {
        return address;
    }
    
    public void setAddress(Address address) {
        this.address = address;
    }
    
    @Override
    public String toString() {
        return "BloggerManageVo{" +
                "bloggerId='" + bloggerId + '\'' +
                ", bloggerName='" + bloggerName + '\'' +
                ", nickname='" + nickname + '\'' +
                ", bloggerAvatar='" + bloggerAvatar + '\'' +
                ", signature='" + signature + '\'' +
                ", registrationDate=" + registrationDate +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", blogNums=" + blogNums +
                ", likeNums=" + likeNums +
                ", address=" + address +
                ", status=" + status +
                '}';
    }
    
    public static BloggerManageVo buildBloggerManageVo(BloggerInfo info, Blogger account){
        BloggerManageVo res = new BloggerManageVo();
        res.setBloggerId(account.get_id());
        res.setBloggerName(account.getAccount());
        res.setBloggerAvatar(info.getAvatar());
        res.setSignature(info.getSignature());
        res.setRegistrationDate(info.getRegistrationDate());
        res.setBlogNums(info.getBlogNums());
        res.setLikeNums(info.getLikeNums());
        res.setPhone(info.getPhone());
        res.setEmail(info.getEmail());
        res.setNickname(info.getNickname());
        res.setAddress(info.getAddress());
        res.setStatus(account.getStatus());
        return res;
    }
}
