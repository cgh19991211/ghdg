package com.gh.ghdg.businessMgr.Repository;

import com.gh.ghdg.businessMgr.bean.entities.BloggerInfo;

import java.util.List;

public interface BloggerInfoRepository extends BaseMongoRepository<BloggerInfo> {
    BloggerInfo findByBloggerNameEquals(String bloggerName);
    
    List<BloggerInfo> findAllByBloggerNameLike(String bloggerName);
}
