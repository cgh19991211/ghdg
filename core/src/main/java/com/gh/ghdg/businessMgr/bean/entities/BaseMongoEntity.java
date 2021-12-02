package com.gh.ghdg.businessMgr.bean.entities;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.data.annotation.Id;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseMongoEntity {
    @Id
    protected String _id;
    
    public BaseMongoEntity() {
    }
    
    public BaseMongoEntity(String _id) {
        this._id = _id;
    }
    
    @JSONField(name = "_id")
    public String get_id() {
        return _id;
    }
    
    @JSONField(name = "_id")
    public void set_id(String _id) {
        this._id = _id;
    }
    
    @Override
    public String toString() {
        return "BaseMongoEntity{" +
                "_id='" + _id + '\'' +
                '}';
    }
}
