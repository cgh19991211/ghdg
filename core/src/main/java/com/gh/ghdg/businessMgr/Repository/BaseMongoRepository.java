package com.gh.ghdg.businessMgr.Repository;

import com.gh.ghdg.businessMgr.bean.entities.BaseMongoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface BaseMongoRepository<T extends BaseMongoEntity> extends MongoRepository<T,String> {
}
