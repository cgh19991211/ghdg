package com.gh.ghdg.businessMgr.bean.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = "category")
public class Category extends BaseMongoEntity{
    private String name;
    private String description;
    private Integer followed_nums;
    private Integer displaySeq;
    private String logo;
    private Set<Label> labels;
    
    public Category() {
    }
    
    public Category(String name, String description, Integer followed_nums, Integer displaySeq, String logo, Set<Label> labels) {
        this.name = name;
        this.description = description;
        this.followed_nums = followed_nums;
        this.displaySeq = displaySeq;
        this.logo = logo;
        this.labels = labels;
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
    
    public Integer getFollowed_nums() {
        return followed_nums;
    }
    
    public void setFollowed_nums(Integer followed_nums) {
        this.followed_nums = followed_nums;
    }
    
    public Integer getDisplaySeq() {
        return displaySeq;
    }
    
    public void setDisplaySeq(Integer displaySeq) {
        this.displaySeq = displaySeq;
    }
    
    public String getLogo() {
        return logo;
    }
    
    public void setLogo(String logo) {
        this.logo = logo;
    }
    
    public Set<Label> getLabels() {
        return labels;
    }
    
    public void setLabels(Set<Label> labels) {
        this.labels = labels;
    }
    
    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", followed_nums=" + followed_nums +
                ", displaySeq=" + displaySeq +
                ", logo='" + logo + '\'' +
                ", labels=" + labels +
                '}';
    }
}
