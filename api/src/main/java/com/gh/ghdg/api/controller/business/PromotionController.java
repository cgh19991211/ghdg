package com.gh.ghdg.api.controller.business;

import com.gh.ghdg.businessMgr.Repository.PromotionRepository;
import com.gh.ghdg.businessMgr.bean.entities.Promotion;
import com.gh.ghdg.businessMgr.service.PromotionService;
import com.gh.ghdg.common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/business/promotion")
public class PromotionController extends BaseMongoController<Promotion, PromotionRepository, PromotionService> {
    @Autowired
    private PromotionService promotionService;
    
    @GetMapping("/all")
    public Result allPromotion(){
        return Result.suc(promotionService.findAll());
    }
    
    @PostMapping("/add")
    public Result addPromotion(@ModelAttribute Promotion promotion){
        return Result.suc(promotionService.addPromotion(promotion));
    }
    
    @PostMapping("/delete")
    public Result deletePromotion(@ModelAttribute Promotion promotion){
        promotionService.deletePromotion(promotion);
        return Result.delSuc();
    }
}
