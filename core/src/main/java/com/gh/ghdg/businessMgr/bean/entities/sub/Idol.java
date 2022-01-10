package com.gh.ghdg.businessMgr.bean.entities.sub;

public class Idol {
    private String id;
    private String name;
    private String avatar;
    private String signature;
    
    public Idol() {
    }
    
    public Idol(String id, String name, String avatar, String signature) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.signature = signature;
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
    
    public String getAvatar() {
        return avatar;
    }
    
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    
    public String getSignature() {
        return signature;
    }
    
    public void setSignature(String signature) {
        this.signature = signature;
    }
    
    @Override
    public String toString() {
        return  "\"id\":\"" + id + '\"' +
                ", \"name\":\"" + name + '\"' +
                ", \"avatar\":\"" + avatar + '\"' +
                ", \"signature\":\"" + signature + '\"';
    }
}
