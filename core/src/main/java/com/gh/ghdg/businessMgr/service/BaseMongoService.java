package com.gh.ghdg.businessMgr.service;

import cn.hutool.core.util.StrUtil;
import com.gh.ghdg.businessMgr.bean.entities.BaseMongoEntity;
import com.gh.ghdg.businessMgr.dao.BaseMongoRepository;
import com.gh.ghdg.common.commonVo.DynamicSpecifications;
import com.gh.ghdg.common.commonVo.Page;
import com.gh.ghdg.common.commonVo.SearchFilter;
import com.gh.ghdg.common.utils.exception.MyException;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.transaction.Transactional;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.regex.Pattern;

public abstract class BaseMongoService<T extends BaseMongoEntity, D extends BaseMongoRepository<T>> {
    
    @Autowired
    D dao;
    
    private Class<T> klass;//实体T的类型
    
    public BaseMongoService(){
        klass = (Class<T>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    
    @Transactional
    public T save(T t){
        return dao.save(t);
    }
    
    @Transactional
    public T update(T t){
        return dao.save(t);
    }
    
    /**
     * 根据id，修改文档对应的键值对
     * @param id
     * @param map
     * @return
     */
    @Transactional
    public UpdateResult update(String id, Map<String,Object> map){
        Update update = null;
        if(StrUtil.isEmpty(id))
            throw new MyException("要修改的mongo实体id不能为空");
        for(Map.Entry<String, Object> entry:map.entrySet()){
            if (update == null) {
                update = Update.update(entry.getKey(), entry.getValue());
            } else {
                update.set(entry.getKey(), entry.getValue());
            }
        }
        return dao.updateFirst(Query.query(Criteria.where("_id").is(id)),update,klass);
    }
    
    @Transactional
    public UpdateResult updateMulti(String id, Map<String,Object> map){
        Update update = null;
        if(StrUtil.isEmpty(id))
            throw new MyException("要修改的mongo实体id不能为空");
        for(Map.Entry<String, Object> entry:map.entrySet()){
            if (update == null) {
                update = Update.update(entry.getKey(), entry.getValue());
            } else {
                update.set(entry.getKey(), entry.getValue());
            }
        }
        return dao.updateMulti(Query.query(Criteria.where("_id").is(id)),update,klass);
    }
    
    /**
     * 根据id返回optional
     * @param id
     * @return
     */
    public T findById(String id){
        return dao.findOne(Query.query(Criteria.where("_id").is(id)),klass);
    }
    
    public List<T> findAll(){
            return dao.findAll(klass);
    }
    
    /**
     * 条件查询
     * @return
     */
    public List<T> findByFilter(List<SearchFilter> filters){
        Query query = parseFilters(filters);
        return dao.find(query,klass);
    }
    public List<T> findByFilter(SearchFilter... filters){
        Query query = parseFilters(filters);
        return dao.find(query,klass);
    }
    public List<T> findByFilter(SearchFilter filter){
        Query query = parseFilters(filter);
        return dao.find(query,klass);
    }
    
    /**
     * 分页查询
     */
    public Page<T> queryPage(Page page){
        List filters = page.getFilters();
        Query query = parseFilters(filters);
        List<T> records = dao.find(query, klass);
        page.setTotal((int)dao.count(query, klass));
        page.setRecords(records);
        return page;
    }
    
    /**
     * 删除
     * @param id
     */
    @Transactional
    public void deleteById(String id){
        dao.remove(Query.query(Criteria.where("_id").is(id)));
    }
    @Transactional
    public void deleteByFilters(SearchFilter... filters){
        dao.remove(parseFilters(filters));
    }
    @Transactional
    public void clear(){
        dao.dropCollection(klass);
        dao.createCollection(klass);
    }
    
    /**
     * 解析自定义的SearchFilter
     * @param filters
     * @return
     */
    protected Query parseFilters(List<SearchFilter> filters){
        Criteria criteria = new Criteria();
        for(SearchFilter filter:filters){
            String fieldName = filter.fieldName;
            Object value = filter.value;
            SearchFilter.Operator operator = filter.operator;
            switch (operator){
                case EQ:
                    criteria.and(fieldName).is(value);
                case LT:
                    criteria.and(fieldName).lt(value);
                case GT:
                    criteria.and(fieldName).gt(value);
                case LTE:
                    criteria.and(fieldName).lte(value);
                case GTE:
                    criteria.and(fieldName).gte(value);
                case LIKE:
                    criteria.and(fieldName).regex(Pattern.compile("%"+value+"%"));
                case IN:
                    criteria.and(fieldName).in(value);
                case ISNULL:
                    criteria.and(fieldName).exists(false);
                case NOTNULL:
                    criteria.and(fieldName).exists(true);
            }
        }
        return Query.query(criteria);
    }
    protected Query parseFilters(SearchFilter... filters){
        return parseFilters(new ArrayList<SearchFilter>(Arrays.asList(filters)));
    }
    protected Query parseFilters(SearchFilter filter){
        ArrayList<SearchFilter> searchFilters = new ArrayList<>();
        searchFilters.add(filter);
        return parseFilters(searchFilters);
    }
    
    protected Pageable pageToPageable(Page page){
        if(page.getSort()!=null)
            return PageRequest.of(page.getCurrent()-1,page.getSize(),page.getSort());
        else
            return PageRequest.of(page.getCurrent()-1,page.getSize(),Sort.Direction.DESC,"_id");
    }
}
