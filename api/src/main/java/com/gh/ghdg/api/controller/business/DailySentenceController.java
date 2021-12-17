package com.gh.ghdg.api.controller.business;

import com.gh.ghdg.businessMgr.Repository.DailySentenceRepository;
import com.gh.ghdg.businessMgr.bean.entities.DailySentence;
import com.gh.ghdg.businessMgr.service.DailySentenceService;
import com.gh.ghdg.common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/business/daily")
public class DailySentenceController extends BaseMongoController<DailySentence, DailySentenceRepository, DailySentenceService>{
    @Autowired
    private DailySentenceService dailySentenceService;
    
    @GetMapping("/today")
    public Result getTodaySentence(){
        return Result.suc(dailySentenceService.getRandom());
    }
    
    @PostMapping("/add")
    public Result addSentence(@ModelAttribute DailySentence dailySentence){
        return Result.suc(dailySentenceService.addSentence(dailySentence));
    }
    
    @PostMapping("/deprecate")
    public Result deprecateSentence(@ModelAttribute DailySentence dailySentence){
        return Result.suc(dailySentenceService.deprecateSentence(dailySentence));
    }
    
    @PostMapping("/invoke")
    public Result invokeSentence(@ModelAttribute DailySentence dailySentence){
        return Result.suc(dailySentenceService.invokeSentence(dailySentence));
    }
}
