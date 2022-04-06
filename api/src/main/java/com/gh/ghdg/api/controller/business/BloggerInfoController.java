package com.gh.ghdg.api.controller.business;

import cn.hutool.core.util.StrUtil;
import com.gh.ghdg.businessMgr.bean.entities.BloggerInfo;
import com.gh.ghdg.businessMgr.Repository.BloggerInfoRepository;
import com.gh.ghdg.businessMgr.bean.entities.sub.FavoriteBlog;
import com.gh.ghdg.businessMgr.bean.entities.sub.Idol;
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
            throw new MyException("博主名字不得为空");
        }
        return Result.suc("add",bloggerInfoService.save(t));
    }
    
    @PostMapping("/update")
    public Result updateBloggerInfo(@ModelAttribute("t") BloggerInfo t){
        if(StrUtil.isEmpty(t.get_id())){
            throw new MyException("博主信息_id不得为空");
        }
        if(t.getBloggerId()==null){
            throw new MyException("博主id不得为空");
        }
        if(StrUtil.isEmpty(t.getBloggerName())){
            throw new MyException("博主名字不得为空");
        }
        return Result.suc("add",bloggerInfoService.save(t));
    }
    
    @GetMapping("/findByBloggerId")
    public Result findInfoById(@RequestParam String id){
        return Result.suc("info by blogger id",bloggerInfoService.findByBloggerId(id));
    }
    
    /**
     * @param name ：博主真名（bloggerName）
     * @return
     */
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
 
    @PostMapping("/followBlogger")
    public void followBlogger(@ModelAttribute Idol idol){
        if(StrUtil.isEmpty(idol.getId()))return;
        bloggerInfoService.followBlogger(idol);
    }
    
    @PostMapping("/unfollowBlogger")
    public void unfollowBlogger(@ModelAttribute Idol idol){
        if(StrUtil.isEmpty(idol.getId()))return;
        bloggerInfoService.unfollowBlogger(idol);
    }
    
    @PostMapping("/favoriteBlog")
    public void favoriteBlog(@ModelAttribute FavoriteBlog favoriteBlog){
        if(StrUtil.isEmpty(favoriteBlog.getBlogId()))return;
        bloggerInfoService.favoriteBlog(favoriteBlog);
    }
    
    @PostMapping("/unfavoriteBlog")
    public void unfavoriteBlog(@ModelAttribute FavoriteBlog favoriteBlog){
        if(StrUtil.isEmpty(favoriteBlog.getBlogId()))return;
        bloggerInfoService.unfavoriteBlog(favoriteBlog);
    }
    
    @PostMapping("/followCategory")
    public void followCategory(@RequestParam String categoryId){
        bloggerInfoService.followCategory(categoryId);
    }
    
    @PostMapping("/unfollowCategory")
    public void unfollowCategory(@RequestParam String categoryId){
        bloggerInfoService.unfollowCategory(categoryId);
    }
    
    /**
     * 是否已关注
     * @param idolId 已关注？的博主的id
     * @return
     */
    @GetMapping("/isFallowed")
    public Result isFallowed(@RequestParam String idolId){
        return Result.suc("是否已关注",bloggerInfoService.isFollowed(idolId));
    }
    
}

