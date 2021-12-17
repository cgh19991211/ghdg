package com.gh.ghdg.business.service;


import com.gh.ghdg.businessMgr.bean.entities.Category;
import com.gh.ghdg.businessMgr.bean.entities.Label;
import com.gh.ghdg.businessMgr.service.CategoryService;
import com.gh.ghdg.businessMgr.service.LabelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

@EnableCaching
@EntityScan(basePackages = "com.gh.ghdg.sysMgr.bean.entities")
@EnableMongoRepositories(basePackages = "com.gh.ghdg.businessMgr.Repository")
@EnableJpaRepositories(basePackages = "com.gh.ghdg.sysMgr.core.dao")
@EnableTransactionManagement
@EnableJpaAuditing
@EnableMongoAuditing
@RunWith(SpringRunner.class)
public class CategoryTest {
    
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private LabelService labelService;
    
    @Test
    public void categoryService(){
        List<Label> java = labelService.findAllLikeName("java");
        Category category = categoryService.findByName("热门");
        category.setLabels(java);
        categoryService.save(category);
    }
}
