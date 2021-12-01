package com.gh.ghdg.businessMgr.service;

import com.gh.ghdg.businessMgr.bean.entities.BaseMongoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

import javax.transaction.Transactional;
import java.lang.reflect.ParameterizedType;

public abstract class BaseMongoService<T extends BaseMongoEntity, ID, D extends MongoRepository<T,ID>>{
    
    @Autowired
    D dao;
    
    private Class<T> klass;//实体T的类型
    
    public BaseMongoService(){
        klass = (Class<T>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    
    public T save(T t){
        return (T) dao.save(t);
    }

}
