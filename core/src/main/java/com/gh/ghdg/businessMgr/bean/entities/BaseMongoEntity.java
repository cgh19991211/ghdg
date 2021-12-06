package com.gh.ghdg.businessMgr.bean.entities;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public class BaseMongoEntity implements Persistable<String> {
    @Id
    protected String _id;
    
    @Version
    protected Integer version;
    
    public BaseMongoEntity() {
    }
    
    public BaseMongoEntity(String _id, Integer version) {
        this._id = _id;
        this.version = version;
    }
    
    @JSONField(name = "_id")
    public String get_id() {
        return _id;
    }
    
    @JSONField(name = "_id")
    public void set_id(String _id) {
        this._id = _id;
    }
    
    public Integer getVersion() {
        return version;
    }
    
    public void setVersion(Integer version) {
        this.version = version;
    }
    
    @Override
    public String toString() {
        return "BaseMongoEntity{" +
                "_id='" + _id + '\'' +
                ", version=" + version +
                '}';
    }
    

    @Override
    public String getId() {
        return _id;
    }
    
    /**
     * Returns if the {@code Persistable} is new or was persisted already.
     *
     * @return if {@literal true} the object is new.
     */
    @Override
    public boolean isNew() {
        return true;
    }
    
}
