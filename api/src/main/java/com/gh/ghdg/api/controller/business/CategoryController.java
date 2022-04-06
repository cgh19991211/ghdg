package com.gh.ghdg.api.controller.business;

import com.gh.ghdg.businessMgr.bean.entities.Category;
import com.gh.ghdg.businessMgr.Repository.CategoryRepository;
import com.gh.ghdg.businessMgr.service.CategoryService;
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
    
    @GetMapping("/findByName")
    public Result findByName(@RequestBody String name){
        return Result.suc("by name",categoryService.findByName(name));
    }
    
    @GetMapping("/findAllLikeName")
    public Result findAllLikeName(@RequestBody String name){
        return Result.suc("like name",categoryService.findAllLikeName(name));
    }
    
    @PostMapping("/add")
    public Result addCategory(@ModelAttribute("t")Category t){
        return Result.suc("add",categoryService.saveCategory(t));
    }
    
    @PostMapping("/delete")
    public Result deleteCategory(@ModelAttribute("t")Category t){
        categoryService.deleteCategory(t);
        return Result.suc("删除成功");
    }
    
    @PostMapping("/assignLabels")
    public Result assignLabels(@RequestParam String categoryId, @RequestParam String labelIds){
        return Result.suc(categoryService.assignLabels(categoryId,labelIds));
    }
    
    @PostMapping("/removeLabels")
    public Result removeLabels(@RequestParam String categoryId, @RequestParam String... labelIds){
        return Result.suc("remove labels",categoryService.removeLabels(categoryId,labelIds));
    }
}
