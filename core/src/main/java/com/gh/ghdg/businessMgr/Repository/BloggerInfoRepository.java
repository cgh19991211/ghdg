package com.gh.ghdg.businessMgr.Repository;

import com.gh.ghdg.businessMgr.bean.entities.BloggerInfo;

import java.util.List;
import java.util.Optional;

public interface BloggerInfoRepository extends BaseMongoRepository<BloggerInfo> {
    BloggerInfo findByBloggerName(String bloggerName);
    
    List<BloggerInfo> findAllByBloggerNameLike(String bloggerName);
    
    Optional<BloggerInfo> findByBloggerId(String bloggerId);
    
}
