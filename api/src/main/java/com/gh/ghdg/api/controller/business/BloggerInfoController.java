package com.gh.ghdg.api.controller.business;

import cn.hutool.core.util.StrUtil;
import com.gh.ghdg.businessMgr.bean.entities.BloggerInfo;
import com.gh.ghdg.businessMgr.Repository.BloggerInfoRepository;
import com.gh.ghdg.businessMgr.service.BloggerInfoService;
import com.gh.ghdg.common.security.JwtUtil;
import com.gh.ghdg.common.utils.Result;
import com.gh.ghdg.common.utils.exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("business/bloggerInfo")
public class BloggerInfoController extends BaseMongoController<BloggerInfo, BloggerInfoRepository, BloggerInfoService> {
    @Autowired
    private BloggerInfoService bloggerInfoService;
    
    @PostMapping("/add")
    public Result saveBloggerInfo(@ModelAttribute("t")BloggerInfo t){
        if(!StrUtil.isEmpty(t.get_id())){
            throw new MyException("不允许自定义id");
        }
        if(t.getBloggerId()==null){
            throw new MyException("博主id不得为空");
        }
        if(StrUtil.isEmpty(t.getBloggerName())){
            throw new MyException("博主账户不得为空");
        }
        return Result.suc("add",bloggerInfoService.save(t));
    }
    
    @PostMapping("/update")
    public Result updateBloggerInfo(@ModelAttribute("t") BloggerInfo t){
        if(StrUtil.isEmpty(t.get_id())){
            throw new MyException("该用户不存在(_id为空)");
        }
        if(t.getBloggerId()==null){
            throw new MyException("博主id不得为空");
        }
        if(StrUtil.isEmpty(t.getBloggerName())){
            throw new MyException("博主账户不得为空");
        }
        return Result.suc("add",bloggerInfoService.save(t));
    }
    
    @GetMapping("/findByName")
    public Result findOneByName(@RequestParam String name){
        return Result.suc("by name",bloggerInfoService.findByName(name));
    }
    
    @GetMapping("/findAllByName")
    public Result findAllByName(@RequestParam String name){
        return Result.suc("like name",bloggerInfoService.findAllByName(name));
    }
    
    @PostMapping("/delete")
    public Result delete(@ModelAttribute("t")BloggerInfo t){
        bloggerInfoService.delete(t);
        return Result.suc("删除成功");
    }
    
    /**
     * 博主添加收藏专栏
     * @param bloggerId
     * @param ids
     * @return
     */
    @PostMapping("/addCategories")
    public Result addFollowedCategories(@RequestParam(required = false) String bloggerId, @RequestParam String... ids){
        if(StrUtil.isEmpty(bloggerId))bloggerId = JwtUtil.getCurBloggerId();
        return Result.suc("add categories",bloggerInfoService.addCategories(bloggerId,ids));
    }
    
    /**
     * 博主移除收藏专栏
     * @param bloggerId
     * @param ids
     * @return
     */
    @PostMapping("/removeCategories")
    public Result removeFollowedCategories(@RequestParam(required = false) String bloggerId, @RequestParam String... ids){
        if(StrUtil.isEmpty(bloggerId))bloggerId = JwtUtil.getCurBloggerId();
        bloggerInfoService.removeCategories(bloggerId,ids);
        return Result.suc("remove favorite categories");
    }
    
    /**
     * 博主收藏博客
     * @param bloggerId
     * @param ids
     * @return
     */
    @PostMapping("/addBlogs")
    public Result addFavoriteBlogs(@RequestParam(required = false) String bloggerId, @RequestParam String... ids){
        if(StrUtil.isEmpty(bloggerId))bloggerId = JwtUtil.getCurBloggerId();
        return Result.suc("add blog",bloggerInfoService.addBlogs(bloggerId,ids));
    }
    
    /**
     * 博主移除收藏的博客
     * @param bloggerId
     * @param ids
     * @return
     */
    @PostMapping("/removeBlogs")
    public Result removeFavoriteBlogs(@RequestParam(required = false) String bloggerId, @RequestParam String... ids){
        if(StrUtil.isEmpty(bloggerId))bloggerId = JwtUtil.getCurBloggerId();
        bloggerInfoService.removeBlogs(bloggerId,ids);
        return Result.suc("remove favorite blogs");
    }
    
    /**
     * 关注博主
     * @param bloggerId
     * @param ids
     * @return
     */
    @PostMapping("/addBloggers")
    public Result addFollowBloggers(@RequestParam(required = false) String bloggerId, @RequestParam String... ids){
        if(StrUtil.isEmpty(bloggerId))bloggerId = JwtUtil.getCurBloggerId();
        return Result.suc("add follow bloggers",bloggerInfoService.addBloggers(bloggerId,ids));
    }
    
    @PostMapping("/removeBloggers")
    public Result removeFollowBloggers(@RequestParam(required = false) String bloggerId, @RequestParam String... ids){
        if(StrUtil.isEmpty(bloggerId))bloggerId = JwtUtil.getCurBloggerId();
        bloggerInfoService.removeBloggers(bloggerId,ids);
        return Result.suc("remove follow bloggers");
    }
    
}

