package com.gh.ghdg.businessMgr.bean.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Document(collection = "category")
public class Category extends BaseMongoEntity{
    private String categoryName;
    private String description;
    private Integer followedNums = 0;
    private Integer displaySeq = 1;
    private String logo;
    private List<Label> labels;
    
    public Category() {
    }
    
    public Category(String categoryName, String description, Integer followedNums, Integer displaySeq, String logo, List<Label> labels) {
        this.categoryName = categoryName;
        this.description = description;
        this.followedNums = followedNums;
        this.displaySeq = displaySeq;
        this.logo = logo;
        this.labels = labels;
    }
    
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
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
    
    public List<Label> getLabels() {
        return labels;
    }
    
    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public Integer getFollowedNums() {
        return followedNums;
    }
    
    public void setFollowedNums(Integer followedNums) {
        this.followedNums = followedNums;
    }
    
    @Override
    public String toString() {
        return
                "_id:\"" + _id + '\"' +
                "categoryName:\"" + categoryName + '\"' +
                ", description:\"" + description + '\"' +
                ", followedNums:\"" + followedNums + '\"' +
                ", displaySeq:\"" + displaySeq + '\"' +
                ", logo:\"" + logo + '\"' +
                ", labels:\"" + labels + + '\"';
    }
    
    @Override
    public boolean isNew(){
        return this.get_id()==null;
    }
}
