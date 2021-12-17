package com.gh.ghdg.api.controller.business;

import com.gh.ghdg.businessMgr.bean.entities.Label;
import com.gh.ghdg.businessMgr.Repository.LabelRepository;
import com.gh.ghdg.businessMgr.service.LabelService;
import com.gh.ghdg.common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("business/label")
public class LabelController extends BaseMongoController<Label, LabelRepository, LabelService>{
    
    @Autowired
    private LabelService labelService;
    
    @GetMapping("/findByName")
    public Result findByName(@RequestBody String name){
        return Result.suc("by name",labelService.findByName(name));
    }
    
    @GetMapping("/findAllLikeName")
    public Result findAllByName(@RequestBody String name){
        return Result.suc("like name",labelService.findAllLikeName(name));
    }
    
    @PostMapping("/add")
    public Result saveLabel(@ModelAttribute("t") Label t){
        return Result.suc("add",labelService.save(t));
    }
    
    @PostMapping("/delete")
    public Result deleteLabel(@ModelAttribute("t") Label t){
        labelService.delete(t);
        return Result.suc("删除成功");
    }
}
