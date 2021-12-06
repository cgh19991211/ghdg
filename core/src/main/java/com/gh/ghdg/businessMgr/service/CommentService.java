package com.gh.ghdg.businessMgr.service;

import com.gh.ghdg.businessMgr.bean.entities.Comment;
import com.gh.ghdg.businessMgr.dao.CommentRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CommentService extends BaseMongoService<Comment, CommentRepository>{
    
    public Comment findByBloggerId(String id){
        return dao.findByBloggerId(id);
    }
    
    public Comment findByBloggerName(String name){
        return dao.findByBloggerName(name);
    }
    
    public List<Comment> findAllLikeContent(String content){
        return dao.findAllByContentLike(content);
    }
    
    @Transactional
    public Comment addComment(Comment comment){
        return dao.save(comment);
    }
    
    @Transactional
    public void deleteComment(Comment comment){
        dao.delete(comment);
    }
    
}
