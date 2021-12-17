package com.gh.ghdg.businessMgr.Repository;

import com.gh.ghdg.businessMgr.bean.entities.Blacklist;

import java.util.List;

public interface BlacklistRepository extends BaseMongoRepository<Blacklist> {
    Blacklist findBlacklistByBloggerName(String bloggerName);
    List<Blacklist> findAllByBloggerNameLike(String bloggerName);
}
