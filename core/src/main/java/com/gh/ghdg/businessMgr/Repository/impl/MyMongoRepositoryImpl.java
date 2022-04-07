package com.gh.ghdg.businessMgr.Repository.impl;

import com.gh.ghdg.businessMgr.bean.entities.BaseMongoEntity;
import com.gh.ghdg.businessMgr.Repository.BaseMongoRepository;
import com.gh.ghdg.common.commonVo.Page;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Created on 2017/12/29 0029.
 *
 * @Author enilu
 */
@Repository
public class MyMongoRepositoryImpl<T extends BaseMongoEntity,D extends BaseMongoRepository> {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void save(BaseMongoEntity entity) {
         mongoTemplate.save(entity);
    }

    public void save(Object data, String collectionName) {
        mongoTemplate.save(data, collectionName);

    }
    
    /**
     * 添加嵌套文档
     * @param map
     * @param klass
     * @param fieldName
     * @param d
     * @param ids
     * @return
     */
    public  UpdateResult add(Map<String,Object> map, Class<T> klass, String fieldName, D d, String... ids){
        Query query = Query.query(criteria(map));
        Update update = new Update();
        List<T> list = new ArrayList<>();
        for(String id:ids){
            Optional<T> byId = d.findById(id);
            if(byId.isPresent()){
                list.add(byId.get());
            }
        }
        update.addToSet(fieldName).each(list);
        return mongoTemplate.updateFirst(query,update, klass);
    }
    
    /**
     * 删除嵌套文档元素
     * @param map
     * @param klass
     * @param fieldName
     * @param d
     * @param ids
     * @param <T>
     * @param <D>
     * @return
     */
    public <T extends BaseMongoEntity, D extends BaseMongoRepository> UpdateResult remove(Map<String,Object> map, Class<T> klass, String fieldName, D d, String... ids){
        Query query = Query.query(criteria(map));
        Update update = new Update();
        List<T> list = new ArrayList<>();
        for(String id:ids){
            Optional<T> byId = d.findById(id);
            if(byId.isPresent()){
                list.add(byId.get());
            }
        }
        update.pullAll(fieldName,list.toArray());
        return mongoTemplate.updateFirst(query,update,klass);
    }

    public void delete(String id, String collectionName) {
        mongoTemplate.remove(Query.query(Criteria.where("_id").is(id)), collectionName);
    }

    public void delete(String collectionName, Map<String, Object> keyValues) {
        mongoTemplate.remove(Query.query(criteria(keyValues)), collectionName);
    }
    public void clear(Class klass){
        mongoTemplate.dropCollection(klass);
        mongoTemplate.createCollection(klass);
    }
    public void clear(String collectionName){
        mongoTemplate.dropCollection(collectionName);
        mongoTemplate.createCollection(collectionName);
    }
    public void update(BaseMongoEntity entity) {
        mongoTemplate.save(entity);
    }


    public UpdateResult update(String id, String collectionName, Map<String, Object> keyValues) {
        Update update = null;
        for (Map.Entry<String, Object> entry : keyValues.entrySet()) {
            if (update == null) {
                update = Update.update(entry.getKey(), entry.getValue());
            } else {
                update.set(entry.getKey(), entry.getValue());
            }
        }
        return mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(id)), update, collectionName);
    }

    public <P> T findOne(Class<T> klass, P value) {
        return findOne(klass, "_id", value);
    }
    public T findOne(Class<T> klass, Map<String,Object> params) {
        Criteria criteria = criteria(params);
        if (criteria != null) {
            return mongoTemplate.findOne(Query.query(criteria), klass);
        }
        return null;
    }
    public T findOne(Class<T> klass, String key, Object value) {
        return mongoTemplate.findOne(Query.query(Criteria.where(key).is(value)), klass);
    }

    public Object findOne(String id, String collectionName) {
        return mongoTemplate.findOne(Query.query(Criteria.where("_id").is(id)), Map.class, collectionName);
    }

    public Map findOne(String collectionName, Object... extraKeyValues) {
        Criteria criteria = criteria(extraKeyValues);
        if (Objects.isNull(criteria)) {
            List<Map> list = mongoTemplate.findAll(Map.class, collectionName);
            if (list != null && !list.isEmpty()) {
                return list.get(0);
            }
            return Collections.emptyMap();
        }
        return mongoTemplate.findOne(Query.query(criteria), Map.class, collectionName);
    }

    public T findOne(Class<T> klass) {
        Query query = new Query();
        return mongoTemplate.findOne(query, klass);

    }

    public T findOne(Class<T> klass, Object... keyValues) {
        Criteria criteria = criteria(keyValues);

        if (criteria == null) {
            List<T> list = mongoTemplate.findAll(klass);
            if (list != null) {
                return list.get(0);
            }
            return null;
        }
        return mongoTemplate.findOne(Query.query(criteria), klass);
    }

    public Page<T> queryPage(Page<T> page, Class<T> klass) {
        return queryPage(page, klass, null);
    }

    public Page<T> queryPage(Page<T> page, Class<T> klass, Map<String, Object> params) {
        Pageable pageable;
        if(page.getSort()==null)
            pageable = PageRequest.of(page.getCurrent() - 1, page.getSize(), Sort.Direction.DESC, "_id");
        else
            pageable = PageRequest.of(page.getCurrent() - 1, page.getSize(),page.getSort());
            Query query = new Query();
        if (params != null && !params.isEmpty()) {
            Criteria criteria = criteria(params);
            query = Query.query(criteria);
        }
        List<T> list = mongoTemplate.find(query.with(pageable), klass);
        Long count = count(klass, params);
        page.setTotal(count.intValue());
        page.setRecords(list);
        return page;
    }
    
    public Page<T> queryPage(Page<T> page, Query query,Class<T> klass){
        if(query==null){
            return queryPage(page,klass);
        }
        Pageable pageable;
        if(page.getSort()==null)
            pageable = PageRequest.of(page.getCurrent() - 1, page.getSize(), Sort.Direction.DESC, "_id");
        else
            pageable = PageRequest.of(page.getCurrent() - 1, page.getSize(),page.getSort());
        List<T> list = mongoTemplate.find(query.with(pageable), klass);
        query.limit(0);//query的limit会限制count的数量，默认是10，改为0是不设限
        query.skip(0);//接下来要做count，所以也不跳过，也不limit
        Long count = mongoTemplate.count(query,klass);
        page.setTotal(count.intValue());
        
        page.setRecords(list);
        return page;
    }

    public List<T> findAll(Class<T> klass) {
        return mongoTemplate.findAll(klass);
    }

    public List<T> findAll(Class<T> klass, Object... keyValues) {
        Criteria criteria = criteria(keyValues);
        return mongoTemplate.find(Query.query(criteria), klass);
    }

    public List<T> findAll(Class<T> klass, Map<String, Object> keyValues) {
        Criteria criteria = criteria(keyValues);
        return mongoTemplate.find(Query.query(criteria), klass);
    }

    public List findAll(String collection) {
        return mongoTemplate.findAll(Map.class, collection);
    }

    public List<Map> findAll(String collectionName, Object... keyValues) {
        Criteria criteria = criteria(keyValues);
        return mongoTemplate.find(Query.query(criteria), Map.class, collectionName);
    }

    /**
     * 查询指定位置附近的商家
     * @param x
     * @param y
     * @param collectionName
     * @param params
     * @param miles 公里数
     * @return
     */
    public GeoResults<Map> near(double x, double y, String collectionName, Map<String, Object> params,Integer miles) {
        Point location = new Point(x, y);
        NearQuery nearQuery = NearQuery.near(location).maxDistance(new Distance(miles, Metrics.MILES));
        if (params != null && !params.isEmpty()) {
            Query query = Query.query(criteria(params));
            nearQuery.query(query);
        }
        try {
            return mongoTemplate.geoNear(nearQuery, Map.class, collectionName);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    /**
     * 查询指定位置附近的商家，默认查询十公里范围内
     * @param x
     * @param y
     * @param collectionName
     * @param params
     * @return
     */
    public GeoResults<Map> near(double x, double y, String collectionName, Map<String, Object> params) {
        return near(x,y,collectionName,params,50);
    }

    public long count(Class klass) {
        return count(klass, null);
    }

    public long count(Class klass, Map<String, Object> params) {
        Criteria criteria = criteria(params);
        if (criteria == null) {

            return mongoTemplate.count(new Query(), klass);
        } else {
            return mongoTemplate.count(Query.query(criteria), klass);
        }
    }

    public long count(String collection) {
        return mongoTemplate.count(null, collection);
    }

    public long count(String collection, Map<String, Object> params) {
        Criteria criteria = criteria(params);
        if (criteria == null) {
            return mongoTemplate.count(null, collection);
        } else {
            return mongoTemplate.count(Query.query(criteria), collection);
        }
    }

    private Criteria criteria(Map<String, Object> map) {
        Criteria criteria = null;
        if (map != null) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (criteria == null) {
                    criteria = Criteria.where(entry.getKey()).is(entry.getValue());
                } else {
                    criteria.and(entry.getKey()).is(entry.getValue());
                }
            }
        }
        return criteria;
    }

    private Criteria criteria(Object... extraKeyValues) {
        Criteria criteria = null;
        if (extraKeyValues.length % 2 != 0) {
            throw new IllegalArgumentException();
        } else {
            for (int i = 0; i < extraKeyValues.length; i += 2) {
                Object k = extraKeyValues[i];
                Object v = extraKeyValues[i + 1];
                if (i == 0) {
                    criteria = Criteria.where(k.toString()).is(v);
                } else {
                    criteria.and(k.toString()).is(v);
                }
            }

        }
        return criteria;
    }
}
