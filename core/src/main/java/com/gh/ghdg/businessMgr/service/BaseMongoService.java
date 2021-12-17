package com.gh.ghdg.businessMgr.service;

import com.gh.ghdg.businessMgr.bean.entities.BaseMongoEntity;
import com.gh.ghdg.businessMgr.Repository.BaseMongoRepository;
import com.gh.ghdg.businessMgr.Repository.impl.MyMongoRepositoryImpl;
import com.gh.ghdg.common.commonVo.Page;
import com.gh.ghdg.common.commonVo.SearchFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.transaction.Transactional;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class BaseMongoService<T extends BaseMongoEntity, S extends BaseMongoRepository<T>> {
    
    protected Class<T> klass;
    
    public BaseMongoService(){
        klass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    
    @Autowired
    protected S dao;
    @Autowired
    protected MyMongoRepositoryImpl template;
    @Autowired
    protected MongoTemplate mongoTemplate;
    @Autowired
    protected MyMongoRepositoryImpl myMongoRepository;
    
    @Transactional
    public T save(T t){
        return dao.save(t);
    }
    
    @Transactional
    public void delete(String id, String collectionName){
        template.delete(id,collectionName);
    }
    
    @Transactional
    protected void delete(T t){
        dao.delete(t);
    }
    
    public List<T> findAll(){
        return mongoTemplate.findAll(klass);
    }
    
    public Page<T> queryPage(Page page){
        if(page.getFilters()==null)
            return myMongoRepository.queryPage(page,klass);
        else{
            Criteria criteria = parstFilters(page.getFilters());
            Query query = Query.query(criteria);
            return myMongoRepository.queryPage(page,query,klass);
        }
    }
    
    protected Criteria parstFilters(List<SearchFilter> filters){
        Criteria criteria = new Criteria();
        for(SearchFilter filter:filters){
            String fieldName = filter.fieldName;
            SearchFilter.Operator operator = filter.operator;
            Object value = filter.value;
            switch (operator){
                case EQ:
                    if(criteria==null){
                        criteria = Criteria.where(fieldName).is(value);
                    }else{
                        criteria.and(fieldName).is(value);
                    }
                case GT:
                    if(criteria==null){
                        criteria = Criteria.where(fieldName).gt(value);
                    }else{
                        criteria.and(fieldName).gt(value);
                    }
                case GTE:
                    if(criteria==null){
                        criteria = Criteria.where(fieldName).gte(value);
                    }else{
                        criteria.and(fieldName).gte(value);
                    }
                case LT:
                    if(criteria==null){
                        criteria = Criteria.where(fieldName).lt(value);
                    }else{
                        criteria.and(fieldName).lt(value);
                    }
                case LTE:
                    if(criteria==null){
                        criteria = Criteria.where(fieldName).lte(value);
                    }else{
                        criteria.and(fieldName).lte(value);
                    }
                case IN:
                    if(criteria==null){
                        criteria = Criteria.where(fieldName).in(value);
                    }else{
                        criteria.and(fieldName).in(value);
                    }
                case ISNULL:
                    if(criteria==null){
                        criteria = Criteria.where(fieldName).exists(false);
                    }else{
                        criteria.and(fieldName).exists(false);
                    }
                case NOTNULL:
                    if(criteria==null){
                        criteria = Criteria.where(fieldName).exists(true);
                    }else{
                        criteria.and(fieldName).exists(true);
                    }
                case LIKE:
                    if(criteria==null){
                        criteria = Criteria.where(fieldName).regex("%"+value+"%");
                    }else{
                        criteria.and(fieldName).regex("%"+value+"%");
                    }
            }
        }
        return criteria;
    }
}
