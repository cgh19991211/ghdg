package com.gh.ghdg.businessMgr.bean.entities;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "blogger")
public class Blogger extends BaseMongoEntity{
    private String username;
    private String password;
    private String salt;
    private String status = "生效";
    
    public Blogger() {
    }
    
    public Blogger(String username, String password, String salt, String status) {
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.status = status;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getSalt() {
        return salt;
    }
    
    public void setSalt(String salt) {
        this.salt = salt;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "Blogger{" +
                "_id='" + _id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
