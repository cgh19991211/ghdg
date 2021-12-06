package com.gh.ghdg.businessMgr.dao;

import com.gh.ghdg.businessMgr.bean.entities.BloggerInfo;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BloggerInfoRepository extends BaseMongoRepository<BloggerInfo> {
    BloggerInfo findByBloggerNameEquals(String bloggerName);
    
    List<BloggerInfo> findAllByBloggerNameLike(String bloggerName);
}
