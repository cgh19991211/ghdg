package com.gh.ghdg.businessMgr.bean.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gh.ghdg.common.enums.Status;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "blogger")
public class Blogger extends BaseMongoEntity{
    private String account;
    private String password;
    private String salt;
    private Status status = Status.生效;//status为0表示被拉黑
    
    public Blogger() {
    }
    
    public Blogger(String account, String password, String salt, Status status) {
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
    
    @JsonIgnore
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    @JsonIgnore
    public String getSalt() {
        return salt;
    }
    
    public void setSalt(String salt) {
        this.salt = salt;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "Blogger{" +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
    
    @Override
    public boolean isNew(){
        return this.get_id()==null;
    }
}
