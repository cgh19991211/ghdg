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
import com.gh.ghdg.common.enums.Status;
import com.gh.ghdg.common.security.JwtUtil;
import com.gh.ghdg.common.utils.HttpKit;
import com.gh.ghdg.common.utils.Result;
import com.gh.ghdg.common.utils.constant.Constants;
import com.gh.ghdg.sysMgr.bean.constant.PermissionCode;
import com.odianyun.util.sensi.SensitiveFilter;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.*;

@RestController
@RequestMapping("business/blog")
public class BlogController extends BaseMongoController<Blog, BlogRepository, BlogService> {
    @Autowired
    private BlogService blogService;
    
    @Autowired
    private BloggerInfoService bloggerInfoService;
    
    @Autowired
    private LabelRepository labelRepository;
    
    @Value("${local.server.port:8080}")
    private String port;
    
    private InetAddress localHost = null;
    
    private SensitiveFilter filter = SensitiveFilter.DEFAULT;
    
    /**
     * 分页 -- 默认根据_id排序
     * @param page
     * @return
     */
    @GetMapping("/queryPage")
    public Result queryByPage(@ModelAttribute Page page, @RequestParam(required = false) String tab){
        page.addFilter("status", SearchFilter.Operator.NEQ, Status.失效);
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
    
    @GetMapping("/list")
    public Result getBlogList(@RequestParam(required = false) Integer curPage,
                                  @RequestParam(required = false) Integer size,
                                  @RequestParam(required = false) String keyword,
                              @RequestParam(required = false) Status status){
        Page page = new Page();
        if(curPage!=null)
            page.setCurrent(curPage);
        if(size!=null)
            page.setSize(size);
        if(keyword!=null)
            page.addFilter("bloggerName", SearchFilter.Operator.EQ,keyword);
        if(status!=null)
            page.addFilter("status", SearchFilter.Operator.EQ,status);
        Page<Blog> blogPage = service.queryPage(page);
        return Result.suc(blogPage);
    }
    
    /**
     * 在个人中心展示博主已发表的博客
     * @param id
     * @return TopicVo
     */
    @GetMapping("/findByBloggerId")
    public Result findByBloggerId(@ModelAttribute Page page,@RequestParam("id") String id){
        
        String[] latest = {"createdDate","lastModifiedDate"};
        Sort sort = Sort.by(Sort.Direction.DESC, latest);
        page.setSort(sort);
        page.addFilter("bloggerId", SearchFilter.Operator.EQ, id);
        page.addFilter("status", SearchFilter.Operator.NEQ,Status.失效);
        
        Page<Blog> blogPage = super.queryPage(page);
        List<TopicVo> data = new ArrayList<>();
        for(Blog blog:blogPage.getRecords()){
            data.add(TopicVo.blogToTopicVo(blog));
        }
        Page<TopicVo> topicVoPage = new Page<>();
        topicVoPage.setTotal(blogPage.getTotal())
                    .setSize(blogPage.getSize())
                    .setCurrent(blogPage.getCurrent())
                    .setRecords(data);
        return Result.suc(topicVoPage);
    }
    
    @GetMapping("/findStoredUpBlog")
    public Result findStoredUpBlog(@ModelAttribute Page page,@RequestParam("id") String id){
        String[] latest = {"createdDate","lastModifiedDate"};
        Sort sort = Sort.by(Sort.Direction.DESC, latest);
        page.setSort(sort);
        page.addFilter("storeUp.bloggerId", SearchFilter.Operator.EQ, id);
        page.addFilter("status", SearchFilter.Operator.NEQ,Status.失效);
    
        Page<Blog> blogPage = super.queryPage(page);
        List<TopicVo> data = new ArrayList<>();
        for(Blog blog:blogPage.getRecords()){
            data.add(TopicVo.blogToTopicVo(blog));
        }
        Page<TopicVo> topicVoPage = new Page<>();
        topicVoPage.setTotal(blogPage.getTotal())
                .setSize(blogPage.getSize())
                .setCurrent(blogPage.getCurrent())
                .setRecords(data);
        return Result.suc(topicVoPage);
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
    public Result findByLabel(@RequestParam String labelId,Integer page, Integer size){
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
        //敏感词过滤
        String content = blogVo.getContent();
        if(StrUtil.isBlank(content)){
            return Result.error(false,"内容不能为空",null,Constants.FAILED);
        }
        String filtedContent = filter.filter(content,'x');
        blog.setContent(filtedContent);
        String filtedSummarize = filter.filter(blogVo.getSummarize(),'x');
        blog.setSummarize(filtedSummarize);
        String filtedTitle = filter.filter(blogVo.getTitle(), 'x');
        blog.setTitle(filtedTitle);
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
        //敏感词过滤
        String filted = filter.filter(blogVo.getContent(),'x');
        String content = filted;
        String filtedSummarize = filter.filter(blogVo.getSummarize(),'x');
        String[] labelIds = blogVo.getLabelIds();
    
        return Result.suc("update success",service.updateBlog(blogId,title,content,filtedSummarize,labelIds));
    }
    
    @PostMapping("/delete")
    public Result deleteBlog(@RequestParam String id) throws IOException {
        if(blogService.deleteById(id)) {
            return Result.suc("删除成功");
        }else{
            return Result.error(false,"删除失败",null, Constants.WITHOUT_PERMISSION);
        }
    }
    
    @PostMapping("/deleteByMgr")
    @RequiresPermissions(value = PermissionCode.BLOG_DELETE)
    public Result deleteBlogByMgr(@RequestParam String id){
        service.delete(id,"blog");
        return Result.suc("delete suc");
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
        page.addFilter("status", SearchFilter.Operator.NEQ,Status.失效);
        return Result.suc(super.queryPage(page));
    }
    
    @PostMapping("/likeBlog")
    public Result likeBlog(@RequestParam String blogId){
        boolean liked = service.likeBlog(blogId);
    
        //添加动态--通过新建http请求的方式，请求dynamic的controller
        try {
            localHost = Inet4Address.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String ip = localHost.getHostAddress();
        
        String dynamicUrl = "http://"+ip+":"+port+"/";
        dynamicUrl += "business/dynamic/addDynamic";
        Map<String,String> params = new HashMap<>();
        params.put("blogId",blogId);
        try{
            params.put("action", URLEncoder.encode("赞了文章","utf-8"));
        }catch (UnsupportedEncodingException e){
            System.out.println("对action的编码失败");
            return Result.error("点赞成功，但是动态添加失败");
        }
        HttpKit.sendPost(dynamicUrl,params);
        return Result.suc("点赞成功",true);
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
        service.favoriteBlog(blogId);
    
        //添加动态--通过新建http请求的方式，请求dynamic的controller
        try {
            localHost = Inet4Address.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String ip = localHost.getHostAddress();
    
        String dynamicUrl = "http://"+ip+":"+port+"/";
        dynamicUrl += "business/dynamic/addDynamic";
        Map<String,String> params = new HashMap<>();
        params.put("blogId",blogId);
        try{
            params.put("action", URLEncoder.encode("收藏了文章","utf-8"));
        }catch (UnsupportedEncodingException e){
            System.out.println("对action的编码失败");
            return Result.error("收藏成功，但是动态添加失败");
        }
        HttpKit.sendPost(dynamicUrl,params);
        return Result.suc("收藏成功",true);
    }
    
    @PostMapping("/cancelFavoriteBlog")
    public Result cancelFavoriteBlog(@RequestParam String blogId){
        if(service.cancelFavoriteBlog(blogId)){
            return Result.suc("cancel favorite success");
        }else{
            return Result.error("cancel favorite failed");
        }
    }
    
    @PostMapping("/freeze")
    @RequiresPermissions(value = PermissionCode.BLOG_FREEZE)
    public Result freezeBlog(String id){
        service.freezeBlog(id);
        return Result.suc("freezed");
    }
    
    @PostMapping("/unfreeze")
    @RequiresPermissions(value = PermissionCode.BLOG_FREEZE)
    public Result unfreezeBlog(String id){
        service.unfreezeBlog(id);
        return Result.suc(null);
    }
    
    @PostMapping("/report")
    public Result reportBlog(String blogId,@RequestParam(defaultValue = "用户没有填写理由") String reason){
        return Result.suc("举报成功，管理员审核通过后将会封禁该博客。",service.reportBlog(blogId,reason));
    }
    
}
