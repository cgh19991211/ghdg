package com.gh.ghdg.businessMgr.service;

import cn.hutool.core.util.StrUtil;
import com.gh.ghdg.businessMgr.bean.entities.*;
import com.gh.ghdg.businessMgr.Repository.*;
import com.gh.ghdg.businessMgr.Repository.impl.MyMongoRepositoryImpl;
import com.gh.ghdg.businessMgr.bean.entities.sub.FavoriteBlog;
import com.gh.ghdg.businessMgr.bean.entities.sub.Idol;
import com.gh.ghdg.common.commonVo.Page;
import com.gh.ghdg.common.security.JwtUtil;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import org.bson.Document;

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

    public BloggerInfo findByBloggerId(String id){
        BloggerInfo bloggerInfo = mongoTemplate.findOne(Query.query(Criteria.where("bloggerId").is(id)), BloggerInfo.class);
        return bloggerInfo;
    }
    
    /**
     * 根据博主真名（bloggerName）获取博主信息
     * @param name
     * @return
     */
    public BloggerInfo findByName(String name){
        return dao.findByBloggerName(name);
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
    public void followBlogger(Idol idol){
        String curBloggerId = JwtUtil.getCurBloggerId();
        BloggerInfo curBloggerInfo = findByBloggerId(curBloggerId);
        
        String command = "{update: \"blogger_info\"," +
                "updates: [{" +
                    "q: {\"bloggerId\":" + curBloggerId + "}" +
                    "u: {$push:{\"idols\":" + idol + "}}" +
                "}]}";
        mongoTemplate.executeCommand(command);
        
        Idol cur = new Idol(curBloggerId,curBloggerInfo.getBloggerName(),curBloggerInfo.getAvatar(),curBloggerInfo.getSignature());
        command = "{update: \"blogger_info\"," +
                "updates: [{" +
                "q: {\"bloggerId\":" + idol.getId() + "}" +
                "u: {$push:{\"fans\":" + cur + "}}" +
                "}]}";
        mongoTemplate.executeCommand(command);
    }
    
    @Transactional
    public void unfollowBlogger(Idol idol){
        String curBloggerId = JwtUtil.getCurBloggerId();
        BloggerInfo curBloggerInfo = findByBloggerId(curBloggerId);
        
        String command = "{update: \"blogger_info\"," +
                "updates: [{" +
                "q: {\"bloggerId\":" + curBloggerId + "}" +
                "u: {$pull:{\"idols\":" + idol + "}}" +
                "}]}";
        mongoTemplate.executeCommand(command);
    
        Idol cur = new Idol(curBloggerId,curBloggerInfo.getBloggerName(),curBloggerInfo.getAvatar(),curBloggerInfo.getSignature());
        command = "{update: \"blogger_info\"," +
                "updates: [{" +
                "q: {\"bloggerId\":" + idol.getId() + "}" +
                "u: {$pull:{\"fans\":" + cur + "}}" +
                "}]}";
        mongoTemplate.executeCommand(command);
    }

    @Transactional
    public void favoriteBlog(FavoriteBlog favoriteBlog){
        String blogId = favoriteBlog.getBlogId();
        Optional<Blog> blogOptional = blogRepository.findById(blogId);
        if(!blogOptional.isPresent())return;
        Blog blog = blogOptional.get();
        String curBloggerId = JwtUtil.getCurBloggerId();
        String command = "{update: \"blogger_info\"," +
                "updates: [{" +
                "q: {\"bloggerId\":" + curBloggerId + "}" +
                "u: {$push:{\"favoriteBlogs\":" + favoriteBlog + "}}" +
                "}]}";
        mongoTemplate.executeCommand(command);
        command = "{update: \"blog\"," +
                "updates: [{" +
                "q: {\"_id\":" + blog.getId() + "}" +
                "u: {$set:{\"favoriteNum\":" + (blog.getStoreUp().size()) + "}}" +
                "}]}";
    }
    
    @Transactional
    public void unfavoriteBlog(FavoriteBlog favoriteBlog){
        String blogId = favoriteBlog.getBlogId();
        Optional<Blog> blogOptional = blogRepository.findById(blogId);
        if(!blogOptional.isPresent())return;
        Blog blog = blogOptional.get();
        String curBloggerId = JwtUtil.getCurBloggerId();
        String command = "{update: \"blogger_info\"," +
                "updates: [{" +
                "q: {\"bloggerId\":" + curBloggerId + "}" +
                "u: {$pull:{\"favoriteBlogs\":" + favoriteBlog + "}}" +
                "}]}";
        mongoTemplate.executeCommand(command);
        command = "{update: \"blog\"," +
                "updates: [{" +
                "q: {\"_id\":" + blog.getId() + "}" +
                "u: {$set:{\"favoriteNum\":" + (blog.getStoreUp().size()) + "}}" +
                "}]}";
        mongoTemplate.executeCommand(command);
    }
    
    @Transactional
    public void followCategory(String categoryId){
        String curBloggerId = JwtUtil.getCurBloggerId();
        Optional<Category> optional = categoryRepository.findById(categoryId);
        if(!optional.isPresent())return;
        Category category = optional.get();
        String command = "{update: \"blogger_info\"," +
                "updates: [{" +
                "q: {\"bloggerId\":" + curBloggerId + "}" +
                "u: {$push:{\"followedCategories\":"+ category + "}}" +
                "}]}";
        mongoTemplate.executeCommand(command);
    }
    
    @Transactional
    public void unfollowCategory(String categoryId){
        String curBloggerId = JwtUtil.getCurBloggerId();
        String command = "{update: \"blogger_info\"," +
                "updates: [{" +
                "q: {\"bloggerId\":" + curBloggerId + "}" +
                "u: {$pull:{\"followedCategories\":{\"_id\""+ categoryId +"}" + "}}" +
                "}]}";
        mongoTemplate.executeCommand(command);
    }
    
    @Transactional
    public Boolean isFollowed(String idolId){
        String curBloggerId = JwtUtil.getCurBloggerId();
        BloggerInfo bloggerInfo = bloggerInfoRepository.findByBloggerId(curBloggerId);
        if(bloggerInfo.getIdols()==null)return false;
        for(Idol idol:bloggerInfo.getIdols()){
            if(StrUtil.equals(idol.getId(),idolId))return true;
        }
        return false;
    }
}
