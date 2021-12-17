package com.gh.ghdg.businessMgr.service;

import com.gh.ghdg.businessMgr.bean.entities.Blacklist;
import com.gh.ghdg.businessMgr.Repository.BlacklistRepository;
import com.gh.ghdg.businessMgr.Repository.impl.MyMongoRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BlacklistService extends BaseMongoService<Blacklist, BlacklistRepository> {
    
    @Autowired
    private MyMongoRepositoryImpl myMongoRepository;
    
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
    
    @Transactional
    public void deleteByBloggerId(String id){
        mongoTemplate.findAndRemove(Query.query(Criteria.where("bloggerId").is(id)),Blacklist.class);
    }

}
