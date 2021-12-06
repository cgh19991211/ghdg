package com.gh.ghdg.businessMgr.service;

import com.gh.ghdg.businessMgr.bean.entities.BloggerInfo;
import com.gh.ghdg.businessMgr.dao.BloggerInfoRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BloggerInfoService extends BaseMongoService<BloggerInfo, BloggerInfoRepository> {
    
    public BloggerInfo findByName(String name){
        return dao.findByBloggerNameEquals(name);
    }
    
    public List<BloggerInfo> findAllByName(String name){
        return dao.findAllByBloggerNameLike(name);
    }
    
    @Transactional
    public BloggerInfo save(BloggerInfo bloggerInfo){
        return dao.save(bloggerInfo);
    }
    
    @Transactional
    public void delete(BloggerInfo bloggerInfo){
        dao.delete(bloggerInfo);
    }
}
