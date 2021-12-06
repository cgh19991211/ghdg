package com.gh.ghdg.api.controller.business;

import com.gh.ghdg.businessMgr.bean.entities.Blog;
import com.gh.ghdg.businessMgr.bean.entities.Category;
import com.gh.ghdg.businessMgr.dao.BlogRepository;
import com.gh.ghdg.businessMgr.service.BlogService;
import com.gh.ghdg.common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("business/blog")
public class BlogController extends BaseMongoController<Blog, BlogRepository, BlogService> {
    @Autowired
    private BlogService blogService;
    
    @GetMapping("/findByBloggerId")
    public Result findByBloggerId(@RequestBody String blogger_id){
        return Result.suc("by blogger id",blogService.findByBloggerId(blogger_id));
    }
    
    @GetMapping("/findByBloggerName")
    public Result findByBloggerName(@RequestBody String blogger_name){
        return Result.suc("by blogger name",blogService.findByBloggerName(blogger_name));
    }
    
    @GetMapping("/findLikeTitle")
    public Result findLikeTitle(@RequestBody String title){
        return Result.suc("like title",blogService.findLikeTitle(title));
    }
    
    @GetMapping("/findByCategory")
    public Result findByCategory(@ModelAttribute("t") Category t){
        return Result.suc("like cate",blogService.findByCategory(t));
    }
    
    @PostMapping("/add")
    public Result saveBlog(@ModelAttribute("t") Blog t){
        return Result.suc("add",blogService.saveBlog(t));
    }
    
    @PostMapping("delete")
    public Result deleteBlog(@ModelAttribute("t")Blog t){
        blogService.deleteBlog(t);
        return Result.suc("删除成功");
    }
}
