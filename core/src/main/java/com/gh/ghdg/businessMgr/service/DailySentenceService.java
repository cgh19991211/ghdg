package com.gh.ghdg.businessMgr.service;

import cn.hutool.core.util.RandomUtil;
import com.gh.ghdg.businessMgr.Repository.DailySentenceRepository;
import com.gh.ghdg.businessMgr.bean.entities.DailySentence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

@Service
public class DailySentenceService extends BaseMongoService<DailySentence, DailySentenceRepository> {
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    public List<DailySentence> getRandom(){
        int rand = RandomUtil.randomInt(10);
        
        return mongoTemplate.find(Query.query(Criteria.where("show")).limit(1).skip(rand),DailySentence.class);
    }
    
    @Transactional
    public DailySentence addSentence(DailySentence dailySentence){
        return mongoTemplate.save(dailySentence);
    }
    
    @Transactional
    public DailySentence deprecateSentence(DailySentence dailySentence){
        dailySentence.setShow(false);
        return mongoTemplate.save(dailySentence);
    }
    
    @Transactional
    public DailySentence invokeSentence(DailySentence dailySentence){
        dailySentence.setShow(true);
        return mongoTemplate.save(dailySentence);
    }
}
