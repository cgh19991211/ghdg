package com.gh.ghdg.businessMgr.service;

import com.gh.ghdg.businessMgr.bean.entities.Blacklist;
import com.gh.ghdg.businessMgr.dao.BlacklistRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BlacklistService extends BaseMongoService<Blacklist, BlacklistRepository> {
    
    public Blacklist findByAccount(String account){
        return dao.findBlacklistByBloggerName(account);
    }
    
    public List<Blacklist> findAllLikeAccount(String account){
        return dao.findAllByBloggerNameLike(account);
    }
    
    @Transactional
    public Blacklist saveBlacklist(Blacklist t){
        return dao.save(t);
    }
    
    @Transactional
    public void deleteBlacklist(Blacklist t){
        dao.delete(t);
    }

}
