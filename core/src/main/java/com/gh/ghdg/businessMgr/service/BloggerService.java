package com.gh.ghdg.businessMgr.service;

import com.gh.ghdg.businessMgr.bean.entities.Blacklist;
import com.gh.ghdg.businessMgr.bean.entities.Blogger;
import com.gh.ghdg.businessMgr.Repository.BloggerRepository;
import com.gh.ghdg.businessMgr.Repository.impl.MyMongoRepositoryImpl;
import com.gh.ghdg.common.commonVo.Page;
import com.gh.ghdg.common.commonVo.SearchFilter;
import com.gh.ghdg.common.enums.Status;
import com.gh.ghdg.common.utils.ToolUtil;
import com.gh.ghdg.common.utils.exception.MyException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BloggerService extends BaseMongoService<Blogger, BloggerRepository>{
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    @Autowired
    private MyMongoRepositoryImpl myMongoRepository;
    
    @Autowired
    private HashedCredentialsMatcher matcher;
    
    @Autowired
    private BlacklistService blacklistService;
    
    public List<Blogger> findAllBloggers(){
        return super.findAll();
    }
    
    public List<Blogger> findAllBanned(){
        return mongoTemplate.find(Query.query(Criteria.where("status").is(0)),Blogger.class);
    }
    public List<Blogger> findAllUnBanned(){
        return mongoTemplate.find(Query.query(Criteria.where("status").ne(0)),Blogger.class);
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
        blogger.setPassword(encryptPassword);
        blogger.setSalt(salt);
        return dao.save(blogger);
    }
    
    @Transactional
    public void signOut(String id){
        //TODO: 用户注销的同时删除该用户信息，但是保留其博客，评论，所以在页面显示时，如果获取到的用户信息为null，则需要给默认值
        
        
        
        mongoTemplate.findAndRemove(Query.query(Criteria.where("_id").is(id)),Blogger.class);
    }
    

    
    
    @Transactional
    public void ban(String id, String reason, Date date){
        Optional<Blogger> optional = dao.findById(id);
        if(!optional.isPresent()){
            throw new MyException("要封禁的用户不存在");
        }
        Blogger blogger = optional.get();
        blogger.setStatus(Status.失效);
        Blacklist blacklist = new Blacklist();
        blacklist.setBloggerId(id);
        blacklist.setBloggerName(blogger.getAccount());
        blacklist.setReason(reason);
        blacklist.setUntil(date);
        //添加进黑名单
        blacklistService.saveBlacklist(blacklist);
        //失效博主
        mongoTemplate.save(blogger);
    }
    
    @Transactional
    public void unBan(String id){
        Optional<Blogger> byId = dao.findById(id);
        if(!byId.isPresent()){
            throw new MyException("要解封的用户不存在");
        }
        Blogger blogger = byId.get();
        blogger.setStatus(Status.生效);
        mongoTemplate.save(blogger);
        blacklistService.deleteByBloggerId(id);
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
