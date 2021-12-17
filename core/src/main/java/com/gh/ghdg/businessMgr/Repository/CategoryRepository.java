package com.gh.ghdg.businessMgr.Repository;

import com.gh.ghdg.businessMgr.bean.entities.Category;

import java.util.List;

public interface CategoryRepository extends BaseMongoRepository<Category> {
    Category findByCategoryName(String name);
    List<Category> findAllByCategoryName(String name);
}
