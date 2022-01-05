package com.gh.ghdg.businessMgr.Repository;

import com.gh.ghdg.businessMgr.bean.entities.Comment;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends BaseMongoRepository<Comment> {
    Comment findByBloggerId(String id);
    Comment findByBloggerName(String name);
    List<Comment> findAllByContentLike(String content);
    List<Comment> findAllByBlogId(String id);
}
