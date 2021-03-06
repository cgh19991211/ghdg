package com.gh.ghdg.businessMgr.service;

import cn.hutool.core.util.StrUtil;
import com.gh.ghdg.businessMgr.Repository.BlogRepository;
import com.gh.ghdg.businessMgr.Repository.BloggerInfoRepository;
import com.gh.ghdg.businessMgr.bean.entities.Blog;
import com.gh.ghdg.businessMgr.bean.entities.BloggerInfo;
import com.gh.ghdg.businessMgr.bean.entities.Comment;
import com.gh.ghdg.businessMgr.Repository.CommentRepository;
import com.gh.ghdg.businessMgr.bean.entities.sub.Response;
import com.gh.ghdg.businessMgr.bean.entities.sub.ThumbsUp;
import com.gh.ghdg.businessMgr.bean.vo.CommentVo;
import com.gh.ghdg.businessMgr.utils.MDToText;
import com.gh.ghdg.common.commonVo.Page;
import com.gh.ghdg.common.enums.Status;
import com.gh.ghdg.common.security.JwtUtil;
import com.gh.ghdg.common.utils.HttpKit;
import com.gh.ghdg.common.utils.Result;
import com.mongodb.client.result.UpdateResult;
import org.bson.codecs.ObjectIdGenerator;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

@Service
public class CommentService extends BaseMongoService<Comment, CommentRepository>{
    
    @Autowired
    private BlogRepository blogRepository;
    
    @Autowired
    private BloggerInfoRepository bloggerInfoRepository;
    
    public Comment findById(String id){
        Optional<Comment> byId = dao.findById(id);
        if(byId.isPresent())
            return byId.get();
        else return null;
    }
    
    public Comment findByBloggerId(String id){
        return dao.findByBloggerId(id);
    }
    
    public List<Comment> findAllByBloggerId(String id){
        return dao.findAllByBloggerId(id);
    }
    
    public List<Comment> findByBlogId(String blogId){
        List<Comment> commentList = dao.findByBlogIdAndLevel(blogId, 1);
        for(Comment comment:commentList){
            String htmlContent = MDToText.mdToHtml(comment.getContent());
            comment.setContent(htmlContent);
        }
        Collections.sort(commentList,(c1,c2)->{
            Date c1CreatedDate = c1.getCreatedDate();
            Date c2CreatedDate = c2.getCreatedDate();
            return c2CreatedDate.compareTo(c1CreatedDate);
        });
        return commentList;
    }
    
    @Override
    public Page<Comment> queryPage(Page page) {
        return super.queryPage(page);
    }
    
    public Comment findByBloggerName(String name){
        return dao.findByBloggerName(name);
    }
    
    public List<Comment> findAllLikeContent(String content){
        return dao.findAllByContentLike(content);
    }
    
    @Transactional
    public Comment addComment(CommentVo commentVo){
        String blogId = commentVo.getTopic_id();
        String content = commentVo.getContent();
        String commentatorId = JwtUtil.getCurBloggerId();
        Integer level = commentVo.getLevel();
    
        Optional<BloggerInfo> optional = bloggerInfoRepository.findByBloggerId(commentatorId);
        if(!optional.isPresent())return null;
        BloggerInfo commentator = optional.get();
        Comment comment = new Comment();
        comment.setBlogId(blogId);
        comment.setContent(content);
        comment.setLevel(level);
    
        comment.setBloggerId(commentator.getBloggerId());
        comment.setBloggerName(commentator.getBloggerName());
        comment.setBloggerAvatar(commentator.getAvatar());
        
        
        
        return dao.save(comment);
    }
    
    @Transactional
    public Boolean addResponse(CommentVo t){
        Comment newComment = addComment(t);
        
    
        Response response = new Response();
        response.setResponseId(newComment.getId());
        Query query = Query.query(Criteria.where("_id").is(t.getCommentId()));
        Update update = new Update();
        update.addToSet("responses",response);
        UpdateResult result = mongoTemplate.updateFirst(query, update, Comment.class);
        
        return result.wasAcknowledged();
    }
    
    public List<Comment> fetchResponses(List<Response> responses){
        List<String> ids = new ArrayList<>();
        for (Response r:responses){
            ids.add(r.getResponseId());
        }
        if(ids.size()==0)return null;
        Query query = Query.query(Criteria.where("_id").in(ids));
        query.addCriteria(Criteria.where("status").ne(Status.??????));
        return mongoTemplate.find(query,Comment.class);
    }
    
    @Transactional
    public void deleteComment(String id) throws IOException {
        String curBloggerId = JwtUtil.getCurBloggerId();
        Optional<Comment> commentOptional = dao.findById(id);
        if(commentOptional.isPresent()){
            Comment comment = commentOptional.get();
            if(StrUtil.equals(curBloggerId,comment.getBloggerId())){
                dao.deleteById(id);
            }else{
                HttpKit.getResponse().sendError(HttpStatus.FORBIDDEN.value());
                System.out.println("======debug=========");
            }
        }
    }
    
    @Transactional
    public boolean likeComment(String commentId){
        try{
            Optional<BloggerInfo> optional = bloggerInfoRepository.findByBloggerId(JwtUtil.getCurBloggerId());
            if(!optional.isPresent())return false;
            BloggerInfo curBlogger = optional.get();
            Query q = Query.query(Criteria.where("_id").is(commentId));
            Update update = new Update();
            ThumbsUp thumbsUp = new ThumbsUp();
            thumbsUp.setBloggerId(curBlogger.getBloggerId());
            thumbsUp.setBloggerName(curBlogger.getBloggerName());
            thumbsUp.setBloggerAvatar(curBlogger.getAvatar());
            update.addToSet("likes",thumbsUp);
            mongoTemplate.updateFirst(q,update,Comment.class);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    @Transactional
    public boolean cancelLike(String commentId){
        try{
            Optional<BloggerInfo> optional = bloggerInfoRepository.findByBloggerId(JwtUtil.getCurBloggerId());
            if(!optional.isPresent())return false;
            BloggerInfo curBlogger = optional.get();
            Query q = Query.query(Criteria.where("_id").is(commentId));
            Update update = new Update();
            ThumbsUp thumbsUp = new ThumbsUp();
            thumbsUp.setBloggerId(curBlogger.getBloggerId());
            thumbsUp.setBloggerName(curBlogger.getBloggerName());
            thumbsUp.setBloggerAvatar(curBlogger.getAvatar());
            update.pull("likes",thumbsUp);
            mongoTemplate.updateFirst(q,update,Comment.class);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    @Transactional
    public void freezeComment(String id){
        Optional<Comment> byId = dao.findById(id);
        Comment comment = null;
        if(byId.isPresent()){
            comment = byId.get();
            comment.setStatus(Status.??????);
        }
        dao.save(comment);
    }
    
    @Transactional
    public void unfreezeComment(String id){
        Optional<Comment> byId = dao.findById(id);
        Comment comment = null;
        if(byId.isPresent()){
            comment = byId.get();
            comment.setStatus(Status.??????);
        }
        dao.save(comment);
    }
    
}
