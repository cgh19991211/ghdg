package com.gh.ghdg.businessMgr.service;

import com.alibaba.fastjson.JSON;
import com.gh.ghdg.businessMgr.Repository.*;
import com.gh.ghdg.businessMgr.Repository.impl.MyMongoRepositoryImpl;
import com.gh.ghdg.businessMgr.bean.entities.*;
import com.gh.ghdg.businessMgr.bean.entities.sub.ThumbsUp;
import com.gh.ghdg.businessMgr.utils.MDToText;
import com.gh.ghdg.common.security.JwtUtil;
import com.gh.ghdg.common.utils.HttpKit;
import com.mongodb.client.result.UpdateResult;
import org.pegdown.PegDownProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

@Service
public class BlogService extends BaseMongoService<Blog, BlogRepository> {
    
    @Autowired
    private LabelRepository labelRepository;
    
    @Autowired
    private CommentRepository commentRepository;
    
    @Resource
    private BloggerInfoRepository bloggerInfoRepository;
    
    @Resource
    private DynamicRepository dynamicRepository;
    
    
    public List<Blog> findByBloggerId(String id){
        return dao.findAllByBloggerId(id);
    }
    
    public Blog findByBloggerName(String name){
        return dao.findByBloggerName(name);
    }
    
    public Optional<Blog> findById(String id){return dao.findById(id);}
    
    public List<Blog> findLikeTitle(String title){
        return dao.findAllByTitleLike(title);
    }
    
    public List<Blog> findByCategory(Category category){
        return dao.findAllByCategory(category);
    }
    
    public List<Blog> findByLabel(String labelId){
        Query query = Query.query(Criteria.where("labels.$[]._id").is(labelId));
        return mongoTemplate.find(query,Blog.class);
    }
    
    @Transactional
    public Blog saveBlog(Blog blog){
        return dao.save(blog);
    }
    
    @Transactional
    public Blog updateBlog(String blogId, String blogTitle, String blogContent, String... labelIds){
        Optional<Blog> blogOptional = dao.findById(blogId);
        if(!blogOptional.isPresent()){
            return null;
        }
        Blog blog = blogOptional.get();
        blog.setTitle(blogTitle);
        blog.setContent(blogContent);
        List<Label> labels = new ArrayList<>();
        for(String id: labelIds){
            Optional<Label> byId = labelRepository.findById(id);
            if(byId.isPresent()){
                labels.add(byId.get());
            }
        }
        blog.setLabels(labels);
        
        return dao.save(blog);
    }
    
    @Transactional
    public void deleteBlog(Blog blog){
        dao.delete(blog);
    }
    
    @Transactional
    public boolean deleteById(String id) throws IOException {
        String curBloggerId = JwtUtil.getCurBloggerId();
        Optional<Blog> blogOptional = dao.findById(id);
        if(!blogOptional.isPresent())return false;
        Blog blog = blogOptional.get();
        if(!blog.getBloggerId().equals(curBloggerId)){
            //TODO: 无权限，返回403
            HttpServletResponse response = HttpKit.getResponse();
            response.sendError(403,"you have no permission to handle this blog");
            return false;
        }
        myTemplate.delete(id,"blog");
        return true;
    }
    
    @Transactional
    public UpdateResult assignLabels(String blogId, String... ids){
        Map<String,Object> map = new HashMap<>();
        map.put("_id",blogId);
        return myTemplate.add(map, Blog.class,"labels",labelRepository,ids);
    }
    
    @Transactional
    public UpdateResult removeLabels(String blogId, String... ids){
        Map<String,Object> map = new HashMap<>();
        map.put("_id",blogId);
        return myTemplate.remove(map, Blog.class,"labels",labelRepository,ids);
    }
    
    @Transactional
    @Async
    public void addViewNum(Blog blog){
        Update update = new Update();
        update.set("viewNums",blog.getViewNums()+1);
        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(blog.get_id())),update,Blog.class);
    }
    
    @Transactional
    public boolean likeBlog(String blogId){
        try {
            BloggerInfo curBlogger = bloggerInfoRepository.findByBloggerId(JwtUtil.getCurBloggerId());
            ThumbsUp thumbsUp = new ThumbsUp();
            thumbsUp.setBloggerId(curBlogger.getBloggerId());
            thumbsUp.setBloggerName(curBlogger.getBloggerName());
            thumbsUp.setBloggerAvatar(curBlogger.getAvatar());
    
            Query query = Query.query(Criteria.where("_id").is(blogId));
            Update update = new Update();
            update.addToSet("likes",thumbsUp);
    
            mongoTemplate.upsert(query, update, Blog.class);
            //TODO:博主点赞数+1
            curBlogger.setLikeNums(curBlogger.getLikeNums()+1);
            bloggerInfoRepository.save(curBlogger);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    
    @Transactional
    public boolean cancelLike(String blogId){
        try{
            String curBloggerId = JwtUtil.getCurBloggerId();
            BloggerInfo cur = bloggerInfoRepository.findByBloggerId(curBloggerId);
            Query query = Query.query(Criteria.where("_id").is(blogId));
            Update update = new Update();
            ThumbsUp thumbsUp = new ThumbsUp();
            thumbsUp.setBloggerId(curBloggerId);
            thumbsUp.setBloggerName(cur.getBloggerName());
            thumbsUp.setBloggerAvatar(cur.getAvatar());
//            update.unset("likes.$");
            update.pull("likes",thumbsUp);
            mongoTemplate.updateFirst(query,update,Blog.class);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    @Transactional
    public boolean favoriteBlog(String blogId){
        try{
            String curBloggerId = JwtUtil.getCurBloggerId();
            BloggerInfo cur = bloggerInfoRepository.findByBloggerId(curBloggerId);
            Query query = Query.query(Criteria.where("_id").is(blogId));
            Update update = new Update();
            ThumbsUp thumbsUp = new ThumbsUp();
            thumbsUp.setBloggerId(curBloggerId);
            thumbsUp.setBloggerName(cur.getBloggerName());
            thumbsUp.setBloggerAvatar(cur.getAvatar());
//            update.unset("likes.$");
            update.addToSet("storeUp",thumbsUp);
            mongoTemplate.updateFirst(query,update,Blog.class);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    @Transactional
    public boolean cancelFavoriteBlog(String blogId){
        try{
            String curBloggerId = JwtUtil.getCurBloggerId();
            BloggerInfo cur = bloggerInfoRepository.findByBloggerId(curBloggerId);
            Query query = Query.query(Criteria.where("_id").is(blogId));
            Update update = new Update();
            ThumbsUp thumbsUp = new ThumbsUp();
            thumbsUp.setBloggerId(curBloggerId);
            thumbsUp.setBloggerName(cur.getBloggerName());
            thumbsUp.setBloggerAvatar(cur.getAvatar());
            update.pull("storeUp",thumbsUp);
            mongoTemplate.updateFirst(query,update,Blog.class);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
