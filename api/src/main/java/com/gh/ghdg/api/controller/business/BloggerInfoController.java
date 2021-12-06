package com.gh.ghdg.api.controller.business;

import com.gh.ghdg.businessMgr.bean.entities.BloggerInfo;
import com.gh.ghdg.businessMgr.dao.BloggerInfoRepository;
import com.gh.ghdg.businessMgr.service.BaseMongoService;
import com.gh.ghdg.businessMgr.service.BloggerInfoService;
import com.gh.ghdg.common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("business/bloggerInfo")
public class BloggerInfoController extends BaseMongoController<BloggerInfo, BloggerInfoRepository, BloggerInfoService> {
    @Autowired
    private BloggerInfoService bloggerInfoService;
    
    @PostMapping("/add")
    public Result saveBloggerInfo(@ModelAttribute("t")BloggerInfo t){
        return Result.suc("add",bloggerInfoService.save(t));
    }
    
    @PostMapping("/update")
    public Result updateBloggerInfo(@ModelAttribute("t")BloggerInfo t){
        return Result.suc("add",bloggerInfoService.save(t));
    }
    
    @GetMapping("/findByName")
    public Result findOneByName(@RequestBody String name){
        return Result.suc("by name",bloggerInfoService.findByName(name));
    }
    
    @GetMapping("/findAllByName")
    public Result findAllByName(@RequestBody String name){
        return Result.suc("like name",bloggerInfoService.findAllByName(name));
    }
    
    @PostMapping("/delete")
    public Result delete(@ModelAttribute("t")BloggerInfo t){
        bloggerInfoService.delete(t);
        return Result.suc("删除成功");
    }
    
}

