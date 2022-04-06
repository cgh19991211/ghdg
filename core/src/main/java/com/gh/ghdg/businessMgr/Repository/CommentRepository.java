package com.gh.ghdg.businessMgr.Repository;

import com.gh.ghdg.businessMgr.bean.entities.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends BaseMongoRepository<Comment> {
    Comment findByBloggerId(String id);
    Comment findByBloggerName(String name);
    List<Comment> findAllByContentLike(String content);
    List<Comment> findByBlogIdAndLevel(String id,Integer level);
}
