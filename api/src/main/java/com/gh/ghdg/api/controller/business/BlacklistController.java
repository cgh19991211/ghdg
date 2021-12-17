package com.gh.ghdg.api.controller.business;

import com.gh.ghdg.businessMgr.bean.entities.Blacklist;
import com.gh.ghdg.businessMgr.Repository.BlacklistRepository;
import com.gh.ghdg.businessMgr.service.BlacklistService;
import com.gh.ghdg.common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("business/blacklist")
public class BlacklistController extends BaseMongoController<Blacklist, BlacklistRepository, BlacklistService>{
    @Autowired
    private BlacklistService blacklistService;
    
    @GetMapping("/findByAccount")
    public Result findByAccount(@RequestBody String account){
        return Result.suc("by account",blacklistService.findByAccount(account));
    }
    
    @GetMapping("/findAllLikeName")
    public Result findAllLikeAccount(@RequestBody String account){
        return Result.suc("like name",blacklistService.findAllLikeAccount(account));
    }
    
    @PostMapping("/add")
    public Result saveBlacklist(@ModelAttribute("t")Blacklist t){
        return Result.suc("add",blacklistService.save(t));
    }
    
    @PostMapping("/delete")
    public Result deleteBlacklist(@ModelAttribute("t")Blacklist t){
        return Result.suc("删除成功");
    }
}
