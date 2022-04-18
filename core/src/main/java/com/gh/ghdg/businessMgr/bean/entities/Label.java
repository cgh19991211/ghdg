package com.gh.ghdg.businessMgr.bean.entities;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "label")
public class Label extends BaseMongoEntity{
    private String name;
    private String description;
    private String icon;
    private Integer blogNums;//与该标签有关的博客数
    
    public Label() {
    }
    
    public Label(String name, String description, String icon, Integer blogNums) {
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.blogNums = blogNums;
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
    
    public Integer getBlogNums() {
        return blogNums;
    }
    
    public void setBlogNums(Integer blogNums) {
        this.blogNums = blogNums;
    }
    
    @Override
    public String toString() {
        return "Label{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", icon='" + icon + '\'' +
                ", blogNums='" + blogNums + '\'' +
                '}';
    }
    
    
    @Override
    public boolean isNew(){
        return this.get_id()==null;
    }
}
