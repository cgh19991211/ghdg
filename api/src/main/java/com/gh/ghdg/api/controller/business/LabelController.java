package com.gh.ghdg.api.controller.business;

import cn.hutool.core.util.StrUtil;
import com.gh.ghdg.businessMgr.bean.entities.Label;
import com.gh.ghdg.businessMgr.Repository.LabelRepository;
import com.gh.ghdg.businessMgr.service.LabelService;
import com.gh.ghdg.common.commonVo.Page;
import com.gh.ghdg.common.commonVo.SearchFilter;
import com.gh.ghdg.common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("business/label")
public class LabelController extends BaseMongoController<Label, LabelRepository, LabelService>{
    
    @Autowired
    private LabelService labelService;
    
    @GetMapping("/list")
    public Result getAll(){
        return Result.suc(service.findAll());
    }
    
    @GetMapping("/page")
    public Result getLabelPage(@RequestParam(required = false) Integer curPage,
                               @RequestParam(required = false) Integer size,
                               @RequestParam(required = false) String keyword){
        Page page = new Page();
        if(curPage!=null)
            page.setCurrent(curPage);
        if(size!=null)
            page.setSize(size);
        if(!StrUtil.isBlank(keyword))
            page.addFilter("name", SearchFilter.Operator.EQ, keyword);
        return Result.suc(service.queryPage(page));
    }
    
    @GetMapping("/findByName")
    public Result findByName(@RequestBody String name){
        return Result.suc("by name",labelService.findByName(name));
    }
    
    @GetMapping("/findAllLikeName")
    public Result findAllByName(@RequestBody String name){
        return Result.suc("like name",labelService.findAllLikeName(name));
    }
    
    @PostMapping("/add")
    public Result saveLabel(@RequestBody Label t){
        return Result.suc("add",labelService.save(t));
    }
    
    @PostMapping("/delete")
    public Result deleteLabel(@RequestBody Label t){
        labelService.delete(t);
        return Result.suc("删除成功");
    }
    
}
