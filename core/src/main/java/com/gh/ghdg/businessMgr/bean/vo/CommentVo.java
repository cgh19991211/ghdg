package com.gh.ghdg.businessMgr.bean.vo;

public class CommentVo {
    String content;
    String topic_id;
    Integer level = 1;
    String commentId = null;//如果level>1，则表明这是一个二级回复，commentId是所回复的评论的id
    
    public CommentVo() {
    }
    
    @Override
    public String toString() {
        return "CommentVo{" +
                "content='" + content + '\'' +
                ", topic_id='" + topic_id + '\'' +
                ", level=" + level +
                ", commentId='" + commentId + '\'' +
                '}';
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getTopic_id() {
        return topic_id;
    }
    
    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }
    
    public Integer getLevel() {
        return level;
    }
    
    public void setLevel(Integer level) {
        this.level = level;
    }
    
    public String getCommentId() {
        return commentId;
    }
    
    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }
    
    public CommentVo(String content, String topic_id, Integer level, String commentId) {
        this.content = content;
        this.topic_id = topic_id;
        this.level = level;
        this.commentId = commentId;
    }
}
