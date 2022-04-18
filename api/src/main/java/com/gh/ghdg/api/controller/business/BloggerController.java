package com.gh.ghdg.api.controller.business;

import cn.hutool.core.util.StrUtil;
import com.gh.ghdg.businessMgr.bean.entities.Blogger;
import com.gh.ghdg.businessMgr.Repository.BloggerRepository;
import com.gh.ghdg.businessMgr.service.BloggerService;
import com.gh.ghdg.common.commonVo.Page;
import com.gh.ghdg.common.commonVo.SearchFilter;
import com.gh.ghdg.common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    public Result deleteBlogger(@RequestParam String id){
        bloggerService.deleteById(id);
        return Result.suc("删除成功");
    }
    
    @GetMapping("/findByAccount")
    public Result findByAccount(@RequestParam String account){
        return Result.suc("by account",bloggerService.findByAccount(account));
    }
    
    
    @GetMapping("/list")
    public Result getBloggerInfoList(
            @RequestParam(required = false) Integer curPage,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String bloggerName){
        Page page = new Page();
        if(curPage!=null)
            page.setCurrent(curPage);
        if(size!=null)
            page.setSize(size);
        if(!StrUtil.isBlank(bloggerName))
            page.addFilter("account", SearchFilter.Operator.EQ,bloggerName);
        
        return Result.suc(service.queryPage(page));
    }
    
    @GetMapping("getAll")
    public Result getAllBloggers(){
        return Result.suc(bloggerService.findAllBloggers());
    }
    
    @GetMapping("/getAllBanned")
    public Result getAllBanned(){
        return Result.suc("banned",bloggerService.findAllBanned());
    }
    
    @GetMapping("/getAllUnBanned")
    public Result getAllUnBanned(){
        return Result.suc("banned",bloggerService.findAllUnBanned());
    }
    
    @PostMapping("/ban")
//    @RequiresPermissions()
    public Result ban(@RequestParam String bloggerId,
                      @RequestParam(required = false) String reason,
                      @RequestParam(required = false) Date date){
        if(StrUtil.isEmpty(reason)){
            reason = "no reason";
        }
        if(date==null){
            date = new Date(System.currentTimeMillis()+1800000);//默认值是三十分钟之后
        }
        bloggerService.ban(bloggerId,reason,date);
        return Result.suc("成功拉进黑名单");
    }
    
    @PostMapping("/unBan")
    public Result unBan(@RequestParam String bloggerId){
        bloggerService.unBan(bloggerId);
        return Result.suc("解封成功");
    }
}
