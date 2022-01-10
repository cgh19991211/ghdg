package com.gh.ghdg.businessMgr.bean.entities.sub;

public class FavoriteBlog {
    private String id;
    private String title;
    private String likeNum;
    private String commentNum;
    
    public FavoriteBlog() {
    }
    
    public FavoriteBlog(String id, String title, String likeNum, String commentNum) {
        this.id = id;
        this.title = title;
        this.likeNum = likeNum;
        this.commentNum = commentNum;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getLikeNum() {
        return likeNum;
    }
    
    public void setLikeNum(String likeNum) {
        this.likeNum = likeNum;
    }
    
    public String getCommentNum() {
        return commentNum;
    }
    
    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
    }
    
    @Override
    public String toString() {
        return  "id:'" + id + '\'' +
                ", title:'" + title + '\'' +
                ", likeNum:'" + likeNum + '\'' +
                ", commentNum:'" + commentNum + '\'';
    }
}
