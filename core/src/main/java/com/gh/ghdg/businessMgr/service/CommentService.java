package com.gh.ghdg.businessMgr.service;

import com.gh.ghdg.businessMgr.bean.entities.Comment;
import com.gh.ghdg.businessMgr.Repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CommentService extends BaseMongoService<Comment, CommentRepository>{
    
    @Autowired
    private BlogService blogService;
    
    public Comment findByBloggerId(String id){
        return dao.findByBloggerId(id);
    }
    
    public List<Comment> findByBlogId(String blogId){
        return dao.findAllByBlogId(blogId);
    }
    
    public Comment findByBloggerName(String name){
        return dao.findByBloggerName(name);
    }
    
    public List<Comment> findAllLikeContent(String content){
        return dao.findAllByContentLike(content);
    }
    
    @Transactional
    public Comment addComment(Comment comment){
        String bloggerId = comment.getBloggerId();
        String blogId = comment.getBlogId();
        blogService.addComment(blogId,comment);
        
        return dao.save(comment);
    }
    
    @Transactional
    public void deleteComment(Comment comment){
        blogService.removeComment(comment);
        dao.delete(comment);
    }
    
}
