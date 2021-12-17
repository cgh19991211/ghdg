package com.gh.ghdg.businessMgr.service;

import com.gh.ghdg.businessMgr.bean.entities.Label;
import com.gh.ghdg.businessMgr.Repository.LabelRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class LabelService extends BaseMongoService<Label, LabelRepository>{
    public Label findByName(String name){
        return dao.findByName(name);
    }
    
    public List<Label> findAllLikeName(String name){
        return dao.findAllByNameLike(name);
    }
    
    @Transactional
    public Label save(Label label){
        return dao.save(label);
    }
    
    @Transactional
    public void delete(Label label){
        dao.delete(label);
    }
    
}
