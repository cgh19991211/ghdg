package com.gh.ghdg.businessMgr.service;

import com.gh.ghdg.businessMgr.bean.entities.Blogger;
import com.gh.ghdg.businessMgr.dao.BloggerRepository;
import com.gh.ghdg.businessMgr.dao.impl.MyMongoRepositoryImpl;
import com.gh.ghdg.common.commonVo.Page;
import com.gh.ghdg.common.commonVo.SearchFilter;
import com.gh.ghdg.common.security.JwtUtil;
import com.gh.ghdg.common.utils.HttpKit;
import com.gh.ghdg.common.utils.ToolUtil;
import com.gh.ghdg.common.utils.exception.MyException;
import com.gh.ghdg.sysMgr.bean.entities.system.User;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BloggerService extends BaseMongoService<Blogger, BloggerRepository>{
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    @Autowired
    private MyMongoRepositoryImpl myMongoRepository;
    
    @Autowired
    private HashedCredentialsMatcher matcher;
    
    public List<Blogger> findAllBloggers(){
        return super.findAll();
    }
    
    public Blogger findByAccount(String account){return dao.findByAccount(account);}
    
    public List<Blogger> findByFilters(List<SearchFilter> filters){
        return mongoTemplate.find(Query.query(parstFilters(filters)),Blogger.class);
    }
    
    public Page queryPage(Page page){
        List filters = page.getFilters();
        if(filters==null||filters.size()==0)
            return myMongoRepository.queryPage(page,Blogger.class);
        else{
            return myMongoRepository.queryPage(page,Query.query(parstFilters(filters)),Blogger.class);
        }
    }
    
    @Transactional
    public Blogger saveBlogger(Blogger blogger){
        return super.save(blogger);
    }
    
    @Transactional
    public void deleteBlogger(Blogger blogger){
        super.delete(blogger);
    }
    
    //TODO: 前台服务
    
    @Transactional
    public Blogger signIn(String account,String password) throws MyException{
        String salt = ToolUtil.getRandomString(4);
        String encryptPassword = encryptPassword(password, salt);
        Blogger blogger = new Blogger();
        blogger.setAccount(account);
        blogger.setPassword(password);
        blogger.setSalt(salt);
        return dao.save(blogger);
    }
    
    @Transactional
    public void signOut(String id){
        //TODO: 用户注销的同时删除该用户信息，但是保留其博客，评论，所以在页面显示时，如果获取到的用户信息为null，则需要给默认值
        
        
        
        mongoTemplate.findAndRemove(Query.query(Criteria.where("_id").is(id)),Blogger.class);
    }
    

    
    
    
    
    
    
    /**
     * 加密密码
     * @param password
     * @param salt
     * @return hashed password
     */
    public String encryptPassword(String password, String salt) {
        return new SimpleHash(matcher.getHashAlgorithmName(), password, salt, matcher.getHashIterations()).toString();
    }
    
}
