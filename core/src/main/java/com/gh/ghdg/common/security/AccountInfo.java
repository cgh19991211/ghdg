package com.gh.ghdg.common.security;


public class AccountInfo {
    private String id;
    private String username;
    
    public AccountInfo() {
    }
    
    public AccountInfo(String id, String username) {
        this.id = id;
        this.username = username;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
}
