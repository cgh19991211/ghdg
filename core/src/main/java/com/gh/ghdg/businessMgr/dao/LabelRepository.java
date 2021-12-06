package com.gh.ghdg.businessMgr.dao;

import com.gh.ghdg.businessMgr.bean.entities.Label;

import java.util.List;

public interface LabelRepository extends BaseMongoRepository<Label> {
    Label findByName(String name);
    List<Label> findAllByNameLike(String name);
}
