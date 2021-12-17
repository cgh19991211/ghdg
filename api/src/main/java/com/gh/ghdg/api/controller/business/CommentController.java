package com.gh.ghdg.api.controller.business;

import com.gh.ghdg.businessMgr.bean.entities.Comment;
import com.gh.ghdg.businessMgr.Repository.CommentRepository;
import com.gh.ghdg.businessMgr.service.CommentService;
import com.gh.ghdg.common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Result saveComment(@ModelAttribute("t")Comment t){
        return Result.suc("add",commentService.addComment(t));
    }
    
    @PostMapping("/delete")
    public Result deleteComment(@ModelAttribute("t") Comment t){
        commentService.deleteComment(t);
        return Result.suc("删除成功");
    }
}
