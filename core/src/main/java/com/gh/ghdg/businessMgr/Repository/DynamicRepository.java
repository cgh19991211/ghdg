package com.gh.ghdg.businessMgr.Repository;

import com.gh.ghdg.businessMgr.bean.entities.Dynamic;

import java.util.List;

public interface DynamicRepository extends BaseMongoRepository<Dynamic>{
    List<Dynamic> findByBelongId(String id);
}
