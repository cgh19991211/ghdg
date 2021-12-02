package com.gh.ghdg.businessMgr.service;

import com.gh.ghdg.businessMgr.bean.entities.Blogger;
import com.gh.ghdg.businessMgr.dao.BloggerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BloggerService extends BaseMongoService<Blogger, BloggerRepository>{
    @Transactional
    public Blogger save(Blogger blogger){
        return super.save(blogger);
    }
}
