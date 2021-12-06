package com.gh.ghdg.businessMgr.bean.entities;

import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "blogger")
public class Blogger extends BaseMongoEntity{
    private String account;
    private String password;
    private String salt;
    private String status = "生效";
    
    public Blogger() {
    }
    
    public Blogger(String account, String password, String salt, String status) {
        this.account = account;
        this.password = password;
        this.salt = salt;
        this.status = status;
    }
    
    public String getAccount() {
        return account;
    }
    
    public void setAccount(String account) {
        this.account = account;
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
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
