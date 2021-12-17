package com.gh.ghdg.businessMgr.service;

import com.gh.ghdg.businessMgr.Repository.PromotionRepository;
import com.gh.ghdg.businessMgr.bean.entities.Promotion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.ProblemReporter;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PromotionService extends BaseMongoService<Promotion, PromotionRepository> {
    @Autowired
    private MongoTemplate mongoTemplate;
    
    public List<Promotion> findAll() {
        return super.findAll();
    }
    
    @Transactional
    public Promotion addPromotion(Promotion promotion){
        return super.save(promotion);
    }
    
    @Transactional
    public void deletePromotion(Promotion promotion){
        super.delete(promotion);
    }
}
