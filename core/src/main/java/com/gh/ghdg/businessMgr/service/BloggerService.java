package com.gh.ghdg.businessMgr.service;

import com.gh.ghdg.businessMgr.bean.entities.Blogger;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BloggerService extends BaseMongoService<Blogger,String, MongoRepository<Blogger,String>>{
    @Transactional
    public Blogger save(Blogger blogger){
        return super.save(blogger);
    }
}
