package com.gh.ghdg.businessMgr.dao;

import com.gh.ghdg.businessMgr.bean.entities.Blogger;
import org.springframework.stereotype.Repository;

public interface BloggerRepository extends BaseMongoRepository<Blogger>,BloggerRepositoryEnhance {

}
