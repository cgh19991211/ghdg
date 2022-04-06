package com.gh.ghdg.businessMgr.Repository;

import com.gh.ghdg.businessMgr.bean.entities.BloggerInfo;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BloggerInfoRepository extends BaseMongoRepository<BloggerInfo> {
    BloggerInfo findByBloggerName(String bloggerName);
    
    List<BloggerInfo> findAllByBloggerNameLike(String bloggerName);
    
    BloggerInfo findByBloggerId(String bloggerId);
}
