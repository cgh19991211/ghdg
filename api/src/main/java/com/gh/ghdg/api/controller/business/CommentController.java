package com.gh.ghdg.api.controller.business;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gh.ghdg.businessMgr.bean.entities.Comment;
import com.gh.ghdg.businessMgr.Repository.CommentRepository;
import com.gh.ghdg.businessMgr.bean.entities.sub.Response;
import com.gh.ghdg.businessMgr.bean.vo.CommentVo;
import com.gh.ghdg.businessMgr.service.CommentService;
import com.gh.ghdg.common.security.JwtUtil;
import com.gh.ghdg.common.utils.Result;
import com.gh.ghdg.common.utils.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("business/comment")
public class CommentController extends BaseMongoController<Comment, CommentRepository, CommentService>{
    @Autowired
    private CommentService commentService;
    
    @GetMapping("/findByBloggerId")
    public Result findByBloggerId(@RequestBody String id){
        return Result.suc("by blogger id",commentService.findByBloggerId(id));
    }
    
    @GetMapping("/findByBlogId")
    public Result findAllCommentsOfBlog(@RequestParam String id){
        return Result.suc("comment of blog",commentService.findByBlogId(id));
    }
    
    @GetMapping("/findByBloggerName")
    public Result findByBloggerName(@RequestBody String name){
        return Result.suc("by blogger name",commentService.findByBloggerName(name));
    }
    
    /**
     * 模糊匹配
     * @param content
     * @return
     */
    @GetMapping("/findAllLikeContent")
    public Result findAllLikeContent(@RequestBody String content){
        return Result.suc("like content",commentService.findAllLikeContent(content));
    }
    
    @PostMapping("/add")
    public Result saveComment(@ModelAttribute CommentVo t){
        if(t.getLevel()==2){
            Boolean result = service.addResponse(t);
            if(result.booleanValue()){
                return Result.suc("回复成功");
            }else{
                return Result.error("回复失败");
            }
        }else{
            return Result.suc("add",commentService.addComment(t));
        }
    }
    
    @PostMapping("/delete")
    public Result deleteComment(String commentId){
        try {
            commentService.deleteComment(commentId);
            return Result.suc("删除成功");
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error(false,HttpStatus.FORBIDDEN.getReasonPhrase(),null,HttpStatus.FORBIDDEN.value());
        }
    }
    
    @PostMapping("/like")
    public Result likeComment(@RequestParam String commentId){
        if(service.likeComment(commentId)){
            return Result.suc("success liked");
        }else{
            return Result.error("like failed");
        }
    }
    
    @PostMapping("/cancelLike")
    public Result cancelLike(@RequestParam String commentId){
        if(service.cancelLike(commentId)){
            return Result.suc("cancel success");
        }else{
            return Result.error("cancel failed");
        }
    }
    
    @PostMapping("/fetchResponses")
    public Result fetchResponses(@RequestBody List<Response> responses){
        return Result.suc("responses",service.fetchResponses(responses));
    }
    
}
