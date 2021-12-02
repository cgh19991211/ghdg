package com.gh.ghdg.businessMgr.dao;

import com.gh.ghdg.businessMgr.bean.entities.BaseMongoEntity;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.repository.NoRepositoryBean;

public interface BaseMongoRepository<T extends BaseMongoEntity> extends MongoOperations, BloggerRepositoryEnhance {
}
