package com.gh.ghdg.api.controller.business;

import com.gh.ghdg.businessMgr.bean.entities.BaseMongoEntity;
import com.gh.ghdg.businessMgr.service.BaseMongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;

public class BaseMongoController<T extends BaseMongoEntity, ID, D extends MongoRepository<T,ID>, R extends BaseMongoService> {
    @Autowired
    protected R service;
    
    public T save(T t){
        return service.save(t);
    }
}
