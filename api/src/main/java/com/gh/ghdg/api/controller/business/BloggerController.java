package com.gh.ghdg.api.controller.business;

import com.gh.ghdg.businessMgr.bean.entities.Blogger;
import com.gh.ghdg.businessMgr.dao.BloggerRepository;
import com.gh.ghdg.businessMgr.service.BloggerService;
import com.gh.ghdg.common.utils.Result;
import com.gh.ghdg.common.utils.exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("business/blogger")
public class BloggerController extends BaseMongoController<Blogger, BloggerRepository, BloggerService> {

    @Autowired
    private BloggerService bloggerService;
    
    
    @PostMapping("/add")
    public Result saveBlogger(@ModelAttribute("t") Blogger blogger){
        return Result.suc(bloggerService.save(blogger));
    }
    @PostMapping("/delete")
    public Result deleteBlogger(@RequestParam String id, @RequestParam String collectionName){
        bloggerService.delete(id,collectionName);
        return Result.suc("删除成功");
    }
    
    
    
    @PostMapping("getAll")
    public Result getAllBloggers(){
        return Result.suc(bloggerService.findAllBloggers());
    }
}
