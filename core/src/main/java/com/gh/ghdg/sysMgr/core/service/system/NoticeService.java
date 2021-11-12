package com.gh.ghdg.sysMgr.core.service.system;

import com.gh.ghdg.sysMgr.BaseService;
import com.gh.ghdg.sysMgr.bean.entities.system.Notice;
import com.gh.ghdg.sysMgr.core.dao.system.NoticeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService extends BaseService<Notice, NoticeDao> {
    @Autowired
    private NoticeDao noticeDao;
    public List<Notice> findByTitleLike(String title){
        return noticeDao.findByTitleLike("%"+title+"%");
    }
}
