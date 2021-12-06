package com.gh.ghdg.businessMgr.service;

import com.gh.ghdg.businessMgr.bean.entities.Blog;
import com.gh.ghdg.businessMgr.bean.entities.Category;
import com.gh.ghdg.businessMgr.dao.BlogRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BlogService extends BaseMongoService<Blog, BlogRepository> {
    public Blog findByBloggerId(String id){
        return dao.findByBloggerId(id);
    }
    
    public Blog findByBloggerName(String name){
        return dao.findByBloggerName(name);
    }
    
    public List<Blog> findLikeTitle(String title){
        return dao.findAllByTitleLike(title);
    }
    
    public List<Blog> findByCategory(Category category){
        return dao.findAllByCategory(category);
    }
    
    @Transactional
    public Blog saveBlog(Blog blog){
        return dao.save(blog);
    }
    
    @Transactional
    public void deleteBlog(Blog blog){
        dao.delete(blog);
    }
}
