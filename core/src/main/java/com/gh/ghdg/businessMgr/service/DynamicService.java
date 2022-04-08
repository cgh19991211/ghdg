package com.gh.ghdg.businessMgr.service;

import cn.hutool.core.util.StrUtil;
import com.gh.ghdg.businessMgr.Repository.BlogRepository;
import com.gh.ghdg.businessMgr.Repository.DynamicRepository;
import com.gh.ghdg.businessMgr.Repository.impl.MyMongoRepositoryImpl;
import com.gh.ghdg.businessMgr.bean.entities.Blog;
import com.gh.ghdg.businessMgr.bean.entities.Dynamic;
import com.gh.ghdg.common.security.JwtUtil;
import org.pegdown.PegDownProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DynamicService extends BaseMongoService<Dynamic, DynamicRepository> {
    
    @Autowired
    private MyMongoRepositoryImpl mongoRepository;
    
    @Resource
    private BlogRepository blogRepository;
    
    public List<Dynamic> getDynamicList(String id){
        List<Dynamic> dynamicList = dao.findByBelongId(id);
        return dynamicList==null?new ArrayList<Dynamic>():dynamicList;
    }
    
    @Transactional
    public void addDynamic(String blogId, String action, String dynamicContent){
        Optional<Blog> blogOptional = blogRepository.findById(blogId);
        if(!blogOptional.isPresent())return;
        Blog blog = blogOptional.get();
        Dynamic dynamic = new Dynamic();
        dynamic.setBelongId(JwtUtil.getCurBloggerId());
        dynamic.setBloggerId(blog.getBloggerId());
        dynamic.setBloggerName(blog.getBloggerName());
        dynamic.setBloggerAvatar(blog.getBloggerAvatar());
        dynamic.setAction(action);
        dynamic.setBlogId(blogId);
        dynamic.setTitle(blog.getTitle());
        String content = null;
        if(StrUtil.isBlank(dynamicContent))
            content = blog.getContent();
        else
            content = dynamicContent;
        PegDownProcessor pdp = new PegDownProcessor(Integer.MAX_VALUE);
        String htmlContent = pdp.markdownToHtml(content);
        dynamic.setContent(htmlContent.length()>200?htmlContent.substring(0,200):htmlContent);
        dao.save(dynamic);
    }
    
    @Transactional
    public void deleteDynamic(String id){
        dao.deleteById(id);
    }
}
