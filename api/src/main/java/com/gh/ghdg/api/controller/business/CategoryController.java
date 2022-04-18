package com.gh.ghdg.api.controller.business;

import cn.hutool.core.util.StrUtil;
import com.gh.ghdg.businessMgr.bean.entities.Category;
import com.gh.ghdg.businessMgr.Repository.CategoryRepository;
import com.gh.ghdg.businessMgr.service.CategoryService;
import com.gh.ghdg.common.commonVo.Page;
import com.gh.ghdg.common.commonVo.SearchFilter;
import com.gh.ghdg.common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("business/category")
public class CategoryController extends BaseMongoController<Category, CategoryRepository, CategoryService>{
    @Autowired
    private CategoryService categoryService;
    
    @GetMapping("/list")
    public Result categoryList(){
        return Result.suc("category list",categoryService.categoryList());
    }
    
    @GetMapping("page")
    public Result categoryList(@RequestParam(required = false) Integer curPage,
                               @RequestParam(required = false) Integer size,
                               @RequestParam(required = false) String keyword){
        Page page = new Page();
        if(curPage!=null)
            page.setCurrent(curPage);
        if(size!=null)
            page.setSize(size);
        if(!StrUtil.isBlank(keyword))
            page.addFilter("categoryName", SearchFilter.Operator.EQ, keyword);
        return Result.suc(service.queryPage(page));
    }
    
    @GetMapping("/findByName")
    public Result findByName(@RequestBody String name){
        return Result.suc("by name",categoryService.findByName(name));
    }
    
    @GetMapping("/findAllLikeName")
    public Result findAllLikeName(@RequestBody String name){
        return Result.suc("like name",categoryService.findAllLikeName(name));
    }
    
    @PostMapping("/add")
//    public Result addCategory(@ModelAttribute("t")Category t){
    public Result addCategory(@RequestBody Category t){
        return Result.suc("add",categoryService.saveCategory(t));
    }
    
    @PostMapping("/delete")
    public Result deleteCategory(@RequestBody Category t){
        categoryService.deleteCategory(t);
        return Result.suc("删除成功");
    }
    
    @PostMapping("/assignLabels")
    public Result assignLabels(@RequestParam String categoryId, @RequestParam String... labelIds){
        return Result.suc(categoryService.assignLabels(categoryId,labelIds));
    }
    
    @PostMapping("/removeLabels")
    public Result removeLabels(@RequestParam String categoryId, @RequestParam String... labelIds){
        return Result.suc("remove labels",categoryService.removeLabels(categoryId,labelIds));
    }
}
