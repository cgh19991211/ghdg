package com.gh.ghdg.businessMgr.dao;

import com.gh.ghdg.businessMgr.bean.entities.Comment;

import java.util.List;

public interface CommentRepository extends BaseMongoRepository<Comment> {
    Comment findByBloggerId(String id);
    Comment findByBloggerName(String name);
    List<Comment> findAllByContentLike(String content);
}
