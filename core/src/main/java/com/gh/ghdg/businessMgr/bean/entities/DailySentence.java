package com.gh.ghdg.businessMgr.bean.entities;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "dailySentence")
public class DailySentence extends BaseMongoEntity{
    private String content;
    private String author;
    private Boolean show = false;
    
    public DailySentence() {
    }
    
    public DailySentence(String content, String author, Boolean show) {
        this.content = content;
        this.author = author;
        this.show = show;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public Boolean getShow() {
        return show;
    }
    
    public void setShow(Boolean show) {
        this.show = show;
    }
    
    @Override
    public String toString() {
        return "DailySentence{" +
                "content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", show=" + show +
                '}';
    }
}
