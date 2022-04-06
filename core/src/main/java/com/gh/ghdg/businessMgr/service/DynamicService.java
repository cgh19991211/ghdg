package com.gh.ghdg.businessMgr.service;

import com.gh.ghdg.businessMgr.Repository.DynamicRepository;
import com.gh.ghdg.businessMgr.Repository.impl.MyMongoRepositoryImpl;
import com.gh.ghdg.businessMgr.bean.entities.Dynamic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class DynamicService extends BaseMongoService<Dynamic, DynamicRepository> {
    
    @Autowired
    private MyMongoRepositoryImpl mongoRepository;
    
    public List<Dynamic> getDynamicList(String id){
        List<Dynamic> dynamicList = dao.findByBelongId(id);
        return dynamicList==null?new ArrayList<Dynamic>():dynamicList;
    }
    
//    @Transactional
//    public void addDynamic(Dynamic dynamic){
//        mongoRepository.save(dynamic);
//    }
}
