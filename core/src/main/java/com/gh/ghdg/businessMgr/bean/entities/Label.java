package com.gh.ghdg.businessMgr.bean.entities;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "label")
public class Label extends BaseMongoEntity{
    private String name;
    private String description;
    private String icon;
    private String blog_nums;
    
    public Label() {
    }
    
    public Label(String name, String description, String icon, String blog_nums) {
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.blog_nums = blog_nums;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getIcon() {
        return icon;
    }
    
    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    public String getBlog_nums() {
        return blog_nums;
    }
    
    public void setBlog_nums(String blog_nums) {
        this.blog_nums = blog_nums;
    }
    
    @Override
    public String toString() {
        return "Label{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", icon='" + icon + '\'' +
                ", blog_nums='" + blog_nums + '\'' +
                '}';
    }
}
