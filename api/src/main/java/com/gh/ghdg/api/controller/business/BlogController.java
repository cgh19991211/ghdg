package com.gh.ghdg.api.controller.business;

import cn.hutool.core.util.StrUtil;
import com.gh.ghdg.businessMgr.Repository.BlogRepository;
import com.gh.ghdg.businessMgr.Repository.LabelRepository;
import com.gh.ghdg.businessMgr.bean.entities.Blog;
import com.gh.ghdg.businessMgr.bean.entities.BloggerInfo;
import com.gh.ghdg.businessMgr.bean.entities.Category;
import com.gh.ghdg.businessMgr.bean.entities.Label;
import com.gh.ghdg.businessMgr.bean.vo.BlogVo;
import com.gh.ghdg.businessMgr.bean.vo.TopicVo;
import com.gh.ghdg.businessMgr.service.BlogService;
import com.gh.ghdg.businessMgr.service.BloggerInfoService;
import com.gh.ghdg.common.commonVo.Page;
import com.gh.ghdg.common.commonVo.SearchFilter;
import com.gh.ghdg.common.security.JwtUtil;
import com.gh.ghdg.common.utils.Result;
import com.gh.ghdg.common.utils.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("business/blog")
public class BlogController extends BaseMongoController<Blog, BlogRepository, BlogService> {
    @Autowired
    private BlogService blogService;
    
    @Autowired
    private BloggerInfoService bloggerInfoService;
    
    @Autowired
    private LabelRepository labelRepository;
    
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
            return queryHotspot(page);
        }else if(StrUtil.equals(tab,"latest")){
            //获取最新
            return queryLatest(page);
        }
        //tab不为"hot"||"latest"
        return Result.suc(super.queryPage(page));
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
    
    /**
     * 在个人中心展示博主已发表的博客
     * @param id
     * @return TopicVo
     */
    @GetMapping("/findByBloggerId")
    public Result findByBloggerId(@RequestParam("id") String id){
        List<Blog> blogList = service.findByBloggerId(id);
        List<TopicVo> data = new ArrayList<>();
        for(Blog blog:blogList){
            data.add(TopicVo.BlogToTopicVo(blog));
        }
        return Result.suc(data);
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
    public Result saveBlog(@RequestBody BlogVo blogVo){
        Blog blog = new Blog();
        String curBloggerId = JwtUtil.getCurBloggerId();
        BloggerInfo bloggerInfo = bloggerInfoService.findByBloggerId(curBloggerId);
        blog.setBloggerId(curBloggerId);
        blog.setBloggerName(bloggerInfo.getBloggerName());
        blog.setBloggerAvatar(bloggerInfo.getAvatar());
        blog.setContent(blogVo.getContent());
        blog.setTitle(blogVo.getTitle());
        String[] labelIds = blogVo.getLabelIds();
        if(labelIds!=null)
            for(String id:labelIds){
                Optional<Label> byId = labelRepository.findById(id);
                if(byId.isPresent()){
                    blog.addLabel(byId.get());
                }
            }
        return Result.suc("add",blogService.saveBlog(blog));
    }
    
    @PostMapping("/update")
    public Result updateBlog(@RequestBody BlogVo blogVo){
        String blogId = blogVo.getBlogId();
        String title = blogVo.getTitle();
        String content = blogVo.getContent();
        String[] labelIds = blogVo.getLabelIds();
    
        return Result.suc("update success",service.updateBlog(blogId,title,content,labelIds));
    }
    
    @PostMapping("/delete")
    public Result deleteBlog(@RequestParam String id) throws IOException {
        if(blogService.deleteById(id)) {
            return Result.suc("删除成功");
        }else{
            return Result.error(false,"删除失败",null, Constants.WITHOUT_PERMISSION);
        }
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
    
    @PostMapping("/likeBlog")
    public Result likeBlog(@RequestParam String blogId){
        return Result.suc("点赞成功",service.likeBlog(blogId));
    }
    
    @PostMapping("/cancelLike")
    public Result cancelLike(@RequestParam String blogId){
        if(service.cancelLike(blogId)){
            return Result.suc("cancel success");
        }else{
            return Result.error("cancel failed");
        }
    }
    
    @PostMapping("/favoriteBlog")
    public Result favoriteBlog(@RequestParam String blogId){
        if(service.favoriteBlog(blogId)){
            return Result.suc("favorite success");
        }else{
            return Result.error("favorite failed");
        }
    }
    
    @PostMapping("/cancelFavoriteBlog")
    public Result cancelFavoriteBlog(@RequestParam String blogId){
        if(service.cancelFavoriteBlog(blogId)){
            return Result.suc("cancel favorite success");
        }else{
            return Result.error("cancel favorite failed");
        }
    }
    
}
