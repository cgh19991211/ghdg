package com.gh.ghdg.businessMgr.service;

import com.gh.ghdg.businessMgr.bean.entities.*;
import com.gh.ghdg.businessMgr.Repository.*;
import com.gh.ghdg.businessMgr.Repository.impl.MyMongoRepositoryImpl;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class BloggerInfoService extends BaseMongoService<BloggerInfo, BloggerInfoRepository> {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private MyMongoRepositoryImpl myMongoRepository;
    
    @Autowired
    private BlogRepository blogRepository;
    
    @Autowired
    private BloggerInfoRepository bloggerInfoRepository;

    public BloggerInfo findById(String id){
        Optional<BloggerInfo> byId = dao.findById(id);
        if(byId.isPresent())return byId.get();
        return null;
    }

    public BloggerInfo findByName(String name){
        return dao.findByBloggerNameEquals(name);
    }
    
    public List<BloggerInfo> findAllByName(String name){
        return dao.findAllByBloggerNameLike(name);
    }
    
    @Transactional
    public BloggerInfo save(BloggerInfo bloggerInfo){
        return mongoTemplate.save(bloggerInfo);
    }
    
    @Transactional
    public void delete(BloggerInfo bloggerInfo){
        dao.delete(bloggerInfo);
    }
    
    @Transactional
    public UpdateResult addCategories(String bloggerId,String... ids){
        Map<String,Object> map = new HashMap<>();
        map.put("bloggerId",bloggerId);
        return myMongoRepository.add(map,BloggerInfo.class,"followedCategories",categoryRepository,ids);
//        return add(bloggerId,BloggerInfo.class,"followedCategories",categoryRepository,ids);
    }
    
    @Transactional
    public UpdateResult removeCategories(String bloggerId, String... ids){
        Map<String,Object> map = new HashMap<>();
        map.put("bloggerId",bloggerId);
        return myMongoRepository.remove(map,BloggerInfo.class,"followedCategories",categoryRepository,ids);
//        return remove(bloggerId,BloggerInfo.class,"followedCategories",categoryRepository,ids);
    }
    
    @Transactional
    public UpdateResult addBlogs(String bloggerId, String... ids){
        Map<String,Object> map = new HashMap<>();
        map.put("bloggerId",bloggerId);
        return myMongoRepository.add(map,BloggerInfo.class,"favoriteBlogs",blogRepository,ids);
//        return add(bloggerId,BloggerInfo.class,"favoriteBlogs",blogRepository,ids);
    }
    
    @Transactional
    public UpdateResult removeBlogs(String bloggerId, String... ids){
        Map<String,Object> map = new HashMap<>();
        map.put("bloggerId",bloggerId);
        return myMongoRepository.remove(map,BloggerInfo.class,"favoriteBlogs",blogRepository,ids);
//        return remove(bloggerId,BloggerInfo.class,"favoriteBlogs",blogRepository,ids);
    }
    
    /**
     *
     * @param bloggerId 进行该操作的博主的id -- 这里使用博主的id是要方便后面进行判断是否有权限
     * @param ids 该博主要关注的的博主的Info的_id
     * @return
     */
    @Transactional
    public UpdateResult addBloggers(String bloggerId, String... ids){
        Map<String,Object> map = new HashMap<>();
        map.put("bloggerId",bloggerId);
        return myMongoRepository.add(map,BloggerInfo.class,"followedBloggers",bloggerInfoRepository,ids);
//        return add(bloggerId,BloggerInfo.class,"followedBloggers",bloggerInfoRepository,ids);
    }
    
    @Transactional
    public UpdateResult removeBloggers(String bloggerId, String... ids){
        Map<String,Object> map = new HashMap<>();
        map.put("bloggerId",bloggerId);
        return myMongoRepository.remove(map,BloggerInfo.class,"followedBloggers",bloggerInfoRepository,ids);
//        return remove(bloggerId,BloggerInfo.class,"followedBloggers",bloggerInfoRepository,ids);
    }
    
//    /**
//     *
//     * @param bloggerId 博主id
//     * @param klass 要修改的文档的klass
//     * @param fieldName 要修改的字段的名字
//     * @param d
//     * @param ids 要添加的字段的id
//     * @param <T> 要修改的字段的类型，比如Blog、Category、Blogger
//     * @param <D> 要修改的字段的类型的dao
//     * @return
//     */
//    @Transactional
//    public <T extends BaseMongoEntity, D extends BaseMongoRepository> UpdateResult add(String bloggerId, Class<T> klass, String fieldName, D d, String... ids){
//        Query query = Query.query(Criteria.where("bloggerId").is(bloggerId));
//        Update update = new Update();
//        List<T> list = new ArrayList<>();
//        for(String id:ids){
//            Optional<T> byId = d.findById(id);
//            if(byId.isPresent()){
//                list.add(byId.get());
//            }
//        }
//        update.addToSet(fieldName).each(list);
//        return mongoTemplate.updateFirst(query,update,BloggerInfo.class);
//    }
//
//    @Transactional
//    public <T extends BaseMongoEntity, D extends BaseMongoRepository> UpdateResult remove(String bloggerId, Class<T> klass, String fieldName, D d, String... ids){
//        Query query = Query.query(Criteria.where("bloggerId").is(bloggerId));
//        Update update = new Update();
//        List<T> list = new ArrayList<>();
//        for(String id:ids){
//            Optional<T> byId = d.findById(id);
//            if(byId.isPresent()){
//                list.add(byId.get());
//            }
//        }
//        update.pullAll(fieldName,list.toArray());
//        return mongoTemplate.updateFirst(query,update,BloggerInfo.class);
//    }

}
