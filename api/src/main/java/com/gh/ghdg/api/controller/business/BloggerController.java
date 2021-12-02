package com.gh.ghdg.api.controller.business;

import com.gh.ghdg.businessMgr.bean.entities.Blogger;
import com.gh.ghdg.businessMgr.dao.BloggerRepository;
import com.gh.ghdg.businessMgr.service.BloggerService;
import com.gh.ghdg.common.utils.Result;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("business/blogger")
public class BloggerController extends BaseMongoController<Blogger, BloggerRepository, BloggerService> {

    @Autowired
    private BloggerService bloggerService;
    
    @PostMapping("/save")
    public Result bloggerSave(@ModelAttribute Blogger blogger){
        return Result.suc(super.save(blogger));
    }
    
    @PostMapping("/resetPassword")
    public Result resetPassword(String id, String password){
        Blogger blogger = bloggerService.findById(id);
        String oldPassword = blogger.getPassword();//已加密的存在数据库中的旧密码
        String salt = blogger.getSalt();
        //TODO: 把传进来的密码按照博主注册账号时加密密码的方式加密，与数据库查询出来的密码做配对
        
        return Result.suc("修改密码成功");
    }
    
    
}
