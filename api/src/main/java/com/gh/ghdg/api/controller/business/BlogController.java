package com.gh.ghdg.api.controller.business;

import cn.hutool.core.util.StrUtil;
import com.gh.ghdg.businessMgr.bean.entities.Blog;
import com.gh.ghdg.businessMgr.bean.entities.Category;
import com.gh.ghdg.businessMgr.Repository.BlogRepository;
import com.gh.ghdg.businessMgr.bean.entities.TestAutoValue;
import com.gh.ghdg.businessMgr.service.BlogService;
import com.gh.ghdg.common.commonVo.Page;
import com.gh.ghdg.common.commonVo.SearchFilter;
import com.gh.ghdg.common.utils.Result;
import org.checkerframework.framework.qual.RequiresQualifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("business/blog")
public class BlogController extends BaseMongoController<Blog, BlogRepository, BlogService> {
    @Autowired
    private BlogService blogService;
    
    /**
     * 分页 -- 默认根据_id排序
     * @param page
     * @return
     */
    @GetMapping("/queryPage")
    public Result queryByPage(@ModelAttribute Page page, @RequestParam(required = false) String tab){
        Sort sort = null;
        if(StrUtil.equals(tab,"hot")){
            //获取最热
            return Result.suc(queryHotspot(page));
        }else if(StrUtil.equals(tab,"latest")){
            //获取最新
            return Result.suc(queryLatest(page));
        }
        //tab不为"hot"||"latest"
        return Result.suc(queryPage(page));
    }

    /**
     * 热门 -- 根据博客评论数、点赞数、访问数进行排序
     * @return
     */
    @GetMapping("/queryHotspot")
    public Result queryHotspot(@ModelAttribute Page page){
        String[] properties = new String[]{"likeNums","viewNums"};
        Sort sort = Sort.by(Sort.Direction.DESC, properties);
        page.setSort(sort);
        return Result.suc(super.queryPage(page));
    }
    
    /**
     * 最新 -- 根据创建日期排序
     * @param page
     * @return
     */
    @GetMapping("/queryLatest")
    public Result queryLatest(@ModelAttribute Page page){
        String[] latest = {"createdDate","lastModifiedDate"};
        Sort sort = Sort.by(Sort.Direction.DESC, latest);
        page.setSort(sort);
        return Result.suc(super.queryPage(page));
    }
    
    
    @GetMapping("/findByBlogId")
    public Result findByBlogId(@RequestParam String id){
        Optional<Blog> optional = blogService.findById(id);
        if(optional.isPresent()){
            Blog blog = optional.get();
            blogService.addViewNum(blog);
            return Result.suc("by blog id",blog);
        }
    
        return Result.error("blog is not exist");
    }
    
    @GetMapping("/findByBloggerName")
    public Result findByBloggerName(@RequestParam String blogger_name){
        return Result.suc("by blogger name",blogService.findByBloggerName(blogger_name));
    }
    
    @GetMapping("/findLikeTitle")
    public Result findLikeTitle(@RequestParam String title){
        return Result.suc("like title",blogService.findLikeTitle(title));
    }
    
    @GetMapping("/findByCategory")
    public Result findByCategory(@ModelAttribute("t") Category t){
        return Result.suc("like cate",blogService.findByCategory(t));
    }
    
    @GetMapping("/findByLabel")
    public Result findByLabel(@RequestParam String labelId){
        return Result.suc("by label id",blogService.findByLabel(labelId));
    }
    
    @PostMapping("/add")
    public Result saveBlog(@ModelAttribute("t") Blog t){
        return Result.suc("add",blogService.saveBlog(t));
    }
    
    @PostMapping("delete")
    public Result deleteBlog(@RequestParam String blogId){
        blogService.deleteById(blogId);
        return Result.suc("删除成功");
    }
    
    @PostMapping("/addLabels")
    public Result addLabels(@RequestParam String blogId, @RequestParam String... ids){
        return Result.suc(blogService.assignLabels(blogId,ids));
    }
    
    @PostMapping("/removeLabels")
    public Result removeLabels(@RequestParam String blogId, @RequestParam String... ids){
        return Result.suc(blogService.removeLabels(blogId,ids));
    }
    
    /**
     * 博客详情页面显示该博主的其他博客列表
     * @return
     */
    @GetMapping("/recommend")
    public Result recommend(@RequestParam String id, @RequestParam String blogId){
        Page page = new Page();
        page.setLimit(10);
        page.addFilter(SearchFilter.build("_id",SearchFilter.Operator.NEQ,blogId));
        page.addFilter(SearchFilter.build("bloggerId",SearchFilter.Operator.EQ,id));
        return Result.suc(super.queryPage(page));
    }
    
    
}
