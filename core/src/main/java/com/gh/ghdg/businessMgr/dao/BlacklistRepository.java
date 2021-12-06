package com.gh.ghdg.businessMgr.dao;

import com.gh.ghdg.businessMgr.bean.entities.Blacklist;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlacklistRepository extends BaseMongoRepository<Blacklist> {
    Blacklist findBlacklistByBloggerName(String bloggerName);
    List<Blacklist> findAllByBloggerNameLike(String bloggerName);
}
