package com.gh.ghdg.sysMgr.core.dao.system;

import com.gh.ghdg.sysMgr.BaseDao;
import com.gh.ghdg.sysMgr.bean.entities.system.Notice;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoticeDao extends BaseDao<Notice> {
    @Query("select s from sys_notice s where s.title like ?1")
    List<Notice> findByTitleLike(String title);
}
