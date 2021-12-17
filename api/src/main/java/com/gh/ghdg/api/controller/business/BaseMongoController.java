package com.gh.ghdg.api.controller.business;

import com.gh.ghdg.businessMgr.bean.entities.BaseMongoEntity;
import com.gh.ghdg.businessMgr.Repository.BaseMongoRepository;
import com.gh.ghdg.businessMgr.service.BaseMongoService;
import com.gh.ghdg.common.commonVo.Page;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class BaseMongoController<T extends BaseMongoEntity, S extends BaseMongoRepository<T>,  R extends BaseMongoService<T,S>> {
    @Autowired
    R service;
    
    public T save(T t){
        return service.save(t);
    }
    
    public void delete(String id, String collectionName){
        service.delete(id,collectionName);
    }
    
    public List<T> findAll(){
        return service.findAll();
    }
    
    public Page<T> queryPage(Page page){
        return service.queryPage(page);
    }
}
