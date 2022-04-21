package com.gh.ghdg.api.controller.business;

import cn.hutool.core.util.StrUtil;
import com.gh.ghdg.businessMgr.Repository.BlogRepository;
import com.gh.ghdg.businessMgr.bean.entities.Blog;
import com.gh.ghdg.businessMgr.bean.entities.Comment;
import com.gh.ghdg.businessMgr.Repository.CommentRepository;
import com.gh.ghdg.businessMgr.bean.entities.sub.Response;
import com.gh.ghdg.businessMgr.bean.vo.CommentVo;
import com.gh.ghdg.businessMgr.bean.vo.TopicVo;
import com.gh.ghdg.businessMgr.service.CommentService;
import com.gh.ghdg.businessMgr.utils.MDToText;
import com.gh.ghdg.common.commonVo.Page;
import com.gh.ghdg.common.commonVo.SearchFilter;
import com.gh.ghdg.common.enums.Status;
import com.gh.ghdg.common.utils.HttpKit;
import com.gh.ghdg.common.utils.Result;
import com.gh.ghdg.sysMgr.bean.constant.PermissionCode;
import com.odianyun.util.sensi.SensitiveFilter;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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
@RequestMapping("business/comment")
public class CommentController extends BaseMongoController<Comment, CommentRepository, CommentService>{
    @Autowired
    private CommentService commentService;
    @Resource
    private BlogRepository blogRepository;
    
    @Value("${local.server.port:8080}")
    private String port;
    
    private InetAddress localHost = null;
    
    private SensitiveFilter filter = SensitiveFilter.DEFAULT;
    
    @GetMapping("/findByBloggerId")
    public Result findByBloggerId(@RequestBody String id){
        return Result.suc("by blogger id",commentService.findByBloggerId(id));
    }
    
    @GetMapping("/commentForProfile")
    public Result findCommentForProfileDisplay(@ModelAttribute Page page, @RequestParam("bloggerId") String bloggerId){
    
        String[] latest = {"createdDate","lastModifiedDate"};
        Sort sort = Sort.by(Sort.Direction.DESC, latest);
        page.setSort(sort);
        page.addFilter("status", SearchFilter.Operator.NEQ, Status.失效);
        page.addFilter("bloggerId", SearchFilter.Operator.EQ, bloggerId);
    
        Page<Comment> commentPage = super.queryPage(page);
    
        List<TopicVo> data = new ArrayList<>();
        for(Comment comment:commentPage.getRecords()){
            String blogId = comment.getBlogId();
            Optional<Blog> byId = blogRepository.findById(blogId);
            if(!byId.isPresent()){
                return Result.error("未知错误，找不到博客");
            }
            Blog blog = byId.get();
            data.add(TopicVo.commentToTopicVo(comment,blog));
        }
    
        Page<TopicVo> topicVoPage = new Page<>();
        topicVoPage.setTotal(commentPage.getTotal())
                .setSize(commentPage.getSize())
                .setCurrent(commentPage.getCurrent())
                .setRecords(data);
        
        return Result.suc(topicVoPage);
    }
    
    @GetMapping("/findByBlogId")
    public Result findAllCommentsOfBlog(@RequestParam String id, @ModelAttribute Page page){
        page.addFilter("blogId", SearchFilter.Operator.EQ,id);
        page.addFilter("level", SearchFilter.Operator.EQ,1);
        page.addFilter("status", SearchFilter.Operator.NEQ,Status.失效);
//        return Result.suc("comment of blog",commentService.findByBlogId(id));
        return Result.suc(service.queryPage(page));
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
    public Result saveComment(@ModelAttribute CommentVo t){
        if(!StrUtil.isBlank(t.getContent()))
            t.setContent(filter.filter(t.getContent(),'x'));
        if(t.getLevel()>1){
            Boolean result = service.addResponse(t);
            if(result.booleanValue()){
                return Result.suc("回复成功");
            }else{
                return Result.error("回复失败");
            }
        }else{
            Comment comment = commentService.addComment(t);
    
            //添加动态--通过新建http请求的方式，请求dynamic的controller -- 感觉这里应该做成服务之间的调用？
            try {
                localHost = Inet4Address.getLocalHost();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            String ip = localHost.getHostAddress();
    
            String dynamicUrl = "http://"+ip+":"+port+"/";
            dynamicUrl += "business/dynamic/addDynamic";
            Map<String,String> params = new HashMap<>();
            params.put("blogId",comment.getBlogId());
            try{
                params.put("action", URLEncoder.encode("点评了文章","utf-8"));
                //comment的content是md格式的，这里只提取纯文本作为展示，否则编码会错误，导致请求连接不上
                String str = MDToText.mdToText(comment.getContent());//pure text
//                String str = MDToText.mdToText(MDToText.mdToHtml(comment.getContent()));//html text,行不通
                params.put("dynamicContent",URLEncoder.encode(str,"utf-8"));
            }catch (UnsupportedEncodingException e){
                System.out.println("对action的编码失败");
                return Result.error("评论成功，但是动态添加失败");
            }
            HttpKit.sendPost(dynamicUrl,params);
            return Result.suc("评论成功");
        }
    }
    
    @PostMapping("/delete")
    public Result deleteComment(String commentId){
        try {
            commentService.deleteComment(commentId);
            return Result.suc("删除成功");
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error(false,HttpStatus.FORBIDDEN.getReasonPhrase(),null,HttpStatus.FORBIDDEN.value());
        }
    }
    
    @PostMapping("/deleteByMgr")
    public Result deleteCommentByMgr(String commentId){
        super.delete(commentId,"comment");
        return Result.suc("删除成功");
    }
    
    @GetMapping("/list")
    public Result commentList(@RequestParam(required = false) Integer curPage,
                              @RequestParam(required = false) Integer size,
                              @RequestParam(required = false) String keyword){
        Page page = new Page();
        if(curPage!=null)
            page.setCurrent(curPage);
        if(size!=null)
            page.setSize(size);
        if(keyword!=null)
            page.addFilter("bloggerName", SearchFilter.Operator.EQ,keyword);
        Page<Comment> commentPage = service.queryPage(page);
        return Result.suc(commentPage);
    }
    
    
    @PostMapping("/freeze")
    public Result freezeComment(String id){
        service.freezeComment(id);
        return Result.suc("freezed");
    }
    
    @PostMapping("/unfreeze")
    public Result unfreezeComment(String id){
        service.unfreezeComment(id);
        return Result.suc(null);
    }
    
    @PostMapping("/like")
    public Result likeComment(@RequestParam String commentId){
        if(service.likeComment(commentId)){
            return Result.suc("success liked");
        }else{
            return Result.error("like failed");
        }
    }
    
    @PostMapping("/cancelLike")
    public Result cancelLike(@RequestParam String commentId){
        if(service.cancelLike(commentId)){
            return Result.suc("cancel success");
        }else{
            return Result.error("cancel failed");
        }
    }
    
    @PostMapping("/fetchResponses")
    public Result fetchResponses(@RequestBody List<Response> responses){
        return Result.suc("responses",service.fetchResponses(responses));
    }
    
}
