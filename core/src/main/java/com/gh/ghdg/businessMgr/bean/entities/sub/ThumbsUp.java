package com.gh.ghdg.businessMgr.bean.entities.sub;

public class ThumbsUp {
    private String bloggerId;//点赞的人的id
    private String bloggerName;
    private String bloggerAvatar;
    
    public ThumbsUp() {
    }
    
    public ThumbsUp(String bloggerId, String bloggerName, String bloggerAvatar) {
        this.bloggerId = bloggerId;
        this.bloggerName = bloggerName;
        this.bloggerAvatar = bloggerAvatar;
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
    
    @Override
    public String toString() {
        return "ThumbsUp{" +
                "bloggerId='" + bloggerId + '\'' +
                ", bloggerName='" + bloggerName + '\'' +
                ", bloggerAvatar='" + bloggerAvatar + '\'' +
                '}';
    }
}
