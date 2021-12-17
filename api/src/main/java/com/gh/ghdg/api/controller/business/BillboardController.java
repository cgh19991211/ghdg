package com.gh.ghdg.api.controller.business;

import com.gh.ghdg.businessMgr.Repository.BillboardRepository;
import com.gh.ghdg.businessMgr.bean.entities.Billboard;
import com.gh.ghdg.businessMgr.service.BillboardService;
import com.gh.ghdg.common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("business/billboard")
public class BillboardController extends BaseMongoController<Billboard, BillboardRepository, BillboardService> {
    @Autowired
    private BillboardService billboardService;
    
    @GetMapping("/show")
    public Result getNotices(){
        return Result.suc(billboardService.getNotices());
    }
    
    @PostMapping("/add")
    public Result addNotice(@ModelAttribute Billboard billboard){
        return Result.suc(billboardService.postNotice(billboard));
    }
    
    @PostMapping("/deprecate")
    public Result deprecateNotice(@ModelAttribute Billboard billboard){
        return Result.suc(billboardService.deprecatedNotice(billboard));
    }
    
    @PostMapping("/invoke")
    public Result invokeNotice(@ModelAttribute Billboard billboard){
        return Result.suc(billboardService.invokeNotice(billboard));
    }
    
}
