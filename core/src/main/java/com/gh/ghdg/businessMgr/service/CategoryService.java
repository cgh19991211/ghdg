package com.gh.ghdg.businessMgr.service;

import cn.hutool.core.util.StrUtil;
import com.gh.ghdg.businessMgr.bean.entities.Category;
import com.gh.ghdg.businessMgr.bean.entities.Label;
import com.gh.ghdg.businessMgr.Repository.CategoryRepository;
import com.gh.ghdg.businessMgr.Repository.LabelRepository;
import com.gh.ghdg.businessMgr.Repository.impl.MyMongoRepositoryImpl;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class CategoryService extends BaseMongoService<Category, CategoryRepository>{
    
    @Autowired
    private MyMongoRepositoryImpl myMongoRepository;
    
    @Autowired
    private LabelRepository labelRepository;
    
    public List<Category> categoryList(){
        return dao.findAll();
    }
    
    public Category findByName(String name){
        return dao.findByCategoryName(name);
    }
    
    public List<Category> findAllLikeName(String name){
        return dao.findAllByCategoryName(name);
    }
    
    @Transactional
    public Category saveCategory(Category category){
        return super.save(category);
    }
    
    @Transactional
    public void deleteCategory(Category category){
        super.delete(category);
    }
    
    /**
     * 为专栏分配标签
     * @param categoryId
     * @param labelIds
     * @return
     */
    @Transactional
    public UpdateResult assignLabels(String categoryId,String... labelIds){
        Query query = Query.query(Criteria.where("_id").is(categoryId));
        Update update = new Update();
        List<Label> labels = new ArrayList<>();
        for(String id:labelIds){
            Optional<Label> optional = labelRepository.findById(id);
            if(optional.isPresent()){
                Label label = optional.get();
                labels.add(label);
            }
        }
        for (Label l:labels)
            update.addToSet("labels",l);
        return mongoTemplate.updateFirst(query,update,Category.class);
    }
    
    @Transactional
    public UpdateResult removeLabels(String categoryId, String... ids){
        Query query = Query.query(Criteria.where("_id").is(categoryId));
        Update update = new Update();
        List<Label> labels = new ArrayList<>();
        for(String id:ids){
            Optional<Label> byId = labelRepository.findById(id);
            if(byId.isPresent()){
                labels.add(byId.get());
            }
        }
        update.pullAll("labels",labels.toArray());
        return mongoTemplate.updateFirst(query,update,Category.class);
    }
}
