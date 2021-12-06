package com.gh.ghdg.businessMgr.dao;

import com.gh.ghdg.businessMgr.bean.entities.Blogger;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BloggerRepository extends BaseMongoRepository<Blogger> {
    Blogger findByAccount(String account);
}
