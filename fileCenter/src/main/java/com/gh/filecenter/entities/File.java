package com.gh.filecenter.entities;
import org.bson.types.Binary;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public class File {
    
    @Id
    private String id;
    private String name; // 文件名
    private Date createdTime; // 上传时间
    private Binary content; // 文件内容
    private String contentType; // 文件类型
    private long size; // 文件大小
    
    public File() {
    }
    
    public File(String id, String name, Date createdTime, Binary content, String contentType, long size) {
        this.id = id;
        this.name = name;
        this.createdTime = createdTime;
        this.content = content;
        this.contentType = contentType;
        this.size = size;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Date getCreatedTime() {
        return createdTime;
    }
    
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
    
    public Binary getContent() {
        return content;
    }
    
    public void setContent(Binary content) {
        this.content = content;
    }
    
    public String getContentType() {
        return contentType;
    }
    
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    
    public long getSize() {
        return size;
    }
    
    public void setSize(long size) {
        this.size = size;
    }
    
    @Override
    public String toString() {
        return "UploadFile{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", createdTime=" + createdTime +
                ", content=" + content +
                ", contentType='" + contentType + '\'' +
                ", size=" + size +
                '}';
    }
}
