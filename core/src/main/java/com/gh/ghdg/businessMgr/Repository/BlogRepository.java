package com.gh.ghdg.businessMgr.Repository;

import com.gh.ghdg.businessMgr.bean.entities.Blog;
import com.gh.ghdg.businessMgr.bean.entities.Category;

import java.util.List;

public interface BlogRepository extends BaseMongoRepository<Blog> {
    List<Blog> findAllByBloggerId(String id);
    Blog findByBloggerName(String name);
    List<Blog> findAllByTitleLike(String title);
    List<Blog> findAllByContentLike(String content);
    List<Blog> findAllByCategory(Category category);
}
