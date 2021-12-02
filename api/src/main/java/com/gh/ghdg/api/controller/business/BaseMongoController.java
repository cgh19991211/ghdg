package com.gh.ghdg.api.controller.business;

import com.gh.ghdg.businessMgr.bean.entities.BaseMongoEntity;
import com.gh.ghdg.businessMgr.dao.BaseMongoRepository;
import com.gh.ghdg.businessMgr.service.BaseMongoService;
import com.gh.ghdg.common.commonVo.Page;
import com.gh.ghdg.common.commonVo.SearchFilter;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Map;

public abstract class BaseMongoController<T extends BaseMongoEntity, D extends BaseMongoRepository<T>, R extends BaseMongoService<T,D>> {
    
    @Autowired
    protected R service;
    
    protected T save(T t){
        return service.save(t);
    }
    
    protected T update(T t){
        return service.update(t);
    }
    
    protected UpdateResult update(String id, Map<String, Object> map){
        return service.update(id,map);
    }
    
    protected UpdateResult updateMulti(String id, Map<String, Object> map){
        return service.updateMulti(id,map);
    }
    
    protected T findById(String id){return service.findById(id);}
    
    protected List<T> findAll(){return service.findAll();}
    
    protected List<T> findByFilter(SearchFilter filter){
        return service.findByFilter(filter);
    }
    protected List<T> findByFilter(List<SearchFilter> filters){
        return service.findByFilter(filters);
    }
    protected List<T> findByFilter(SearchFilter... filters){
        return service.findByFilter(filters);
    }
    
    protected Page<T> queryPage(Page page){
        return service.queryPage(page);
    }
    
    protected void deleteById(String id){
        service.deleteById(id);
    }
    protected void deleteByFilters(SearchFilter... filters){
        service.deleteByFilters(filters);
    }
    
    protected void clear(){
        service.clear();
    }
}
