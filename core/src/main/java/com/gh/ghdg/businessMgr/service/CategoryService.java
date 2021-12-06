package com.gh.ghdg.businessMgr.service;

import com.gh.ghdg.businessMgr.bean.entities.Category;
import com.gh.ghdg.businessMgr.dao.CategoryRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CategoryService extends BaseMongoService<Category, CategoryRepository>{
    
    public Category findByName(String name){
        return dao.findByCategoryName(name);
    }
    
    public List<Category> findAllLikeName(String name){
        return dao.findAllByCategoryName(name);
    }
    
    @Transactional
    public Category saveCategory(Category category){
        return dao.save(category);
    }
    
    @Transactional
    public void deleteCategory(Category category){
        dao.delete(category);
    }
    
}
