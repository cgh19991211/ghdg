package com.gh.ghdg.businessMgr.Repository;

import com.gh.ghdg.businessMgr.bean.entities.Blogger;

public interface BloggerRepository extends BaseMongoRepository<Blogger> {
    Blogger findByAccount(String account);
}
