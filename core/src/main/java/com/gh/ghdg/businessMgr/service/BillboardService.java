package com.gh.ghdg.businessMgr.service;

import com.gh.ghdg.businessMgr.Repository.BillboardRepository;
import com.gh.ghdg.businessMgr.bean.entities.Billboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BillboardService extends BaseMongoService<Billboard, BillboardRepository>{
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    public List<Billboard> getNotices(){
        return mongoTemplate.find(Query.query(Criteria.where("show")),Billboard.class);
    }
    
    @Transactional
    public Billboard postNotice(Billboard billboard){
        return dao.save(billboard);
    }
    
    @Transactional
    public Billboard deprecatedNotice(Billboard billboard){
        billboard.setShow(false);
        return mongoTemplate.save(billboard);
    }
    
    @Transactional
    public Billboard invokeNotice(Billboard billboard){
        billboard.setShow(true);
        return mongoTemplate.save(billboard);
    }
}
