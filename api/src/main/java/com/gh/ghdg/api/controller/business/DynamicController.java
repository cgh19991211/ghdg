package com.gh.ghdg.api.controller.business;

import com.gh.ghdg.businessMgr.Repository.DynamicRepository;
import com.gh.ghdg.businessMgr.bean.entities.Dynamic;
import com.gh.ghdg.businessMgr.service.DynamicService;
import com.gh.ghdg.common.utils.Result;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("business/dynamic")
public class DynamicController extends BaseMongoController<Dynamic, DynamicRepository, DynamicService> {
    @GetMapping("/dynamicList/{id}")
    public Result dynamicList(@PathVariable String id){
        return Result.suc("个人动态",service.getDynamicList(id));
    }
    
}
