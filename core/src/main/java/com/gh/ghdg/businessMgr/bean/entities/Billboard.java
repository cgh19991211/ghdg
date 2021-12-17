package com.gh.ghdg.businessMgr.bean.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "billboard")
public class Billboard extends BaseMongoEntity{
    private  String content;
    private Date createdDate;
    private Boolean show = false;
    
    public Billboard() {
    }
    
    public Billboard(String content, Date createdDate, Boolean show) {
        this.content = content;
        this.createdDate = createdDate;
        this.show = show;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public Date getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    
    public Boolean getShow() {
        return show;
    }
    
    public void setShow(Boolean show) {
        this.show = show;
    }
    
    @Override
    public String toString() {
        return "Billboard{" +
                "content='" + content + '\'' +
                ", createdDate=" + createdDate +
                ", show=" + show +
                '}';
    }
}
