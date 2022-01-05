package com.gh.ghdg.businessMgr.service;

import com.gh.ghdg.businessMgr.bean.entities.Blog;
import com.gh.ghdg.businessMgr.bean.entities.Category;
import com.gh.ghdg.businessMgr.bean.entities.Comment;
import com.gh.ghdg.businessMgr.Repository.BlogRepository;
import com.gh.ghdg.businessMgr.Repository.CommentRepository;
import com.gh.ghdg.businessMgr.Repository.LabelRepository;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class BlogService extends BaseMongoService<Blog, BlogRepository> {
    
    @Autowired
    private LabelRepository labelRepository;
    
    @Autowired
    private CommentRepository commentRepository;
    
    
    public Blog findByBloggerId(String id){
        return dao.findByBloggerId(id);
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
    public void deleteBlog(Blog blog){
        dao.delete(blog);
    }
    
    @Transactional
    public void deleteById(String id){
        myMongoRepository.delete(id,"blog");
    }
    
    @Transactional
    public UpdateResult assignLabels(String blogId, String... ids){
        Map<String,Object> map = new HashMap<>();
        map.put("_id",blogId);
        return myMongoRepository.add(map, Blog.class,"labels",labelRepository,ids);
    }
    
    @Transactional
    public UpdateResult removeLabels(String blogId, String... ids){
        Map<String,Object> map = new HashMap<>();
        map.put("_id",blogId);
        return myMongoRepository.remove(map, Blog.class,"labels",labelRepository,ids);
    }
    
//    @Transactional
//    public void addComment(String blogId, Comment comment){
//        Optional<Blog> byId = dao.findById(blogId);
//        if(!byId.isPresent())return;
//        Blog blog = byId.get();
//        if(blog.getComments()==null){
//            blog.setComments(new ArrayList<>());
//        }
//        blog.getComments().add(comment);
//        mongoTemplate.save(blog);
//    }

//    @Transactional
//    public void removeComment(Comment comment){
//        Map<String,Object> map = new HashMap<>();
//        map.put("blogId",comment.blogId());
//        myMongoRepository.remove(map,Blog.class,"comments",commentRepository,comment.get_id());
//    }
    
    @Transactional
    public void addViewNum(Blog blog){
        Update update = new Update();
        update.set("viewNums",blog.getViewNums()+1);
        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(blog.get_id())),update,Blog.class);
    }
}
