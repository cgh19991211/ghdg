package com.gh.ghdg.api.controller.business;

import com.gh.ghdg.businessMgr.Repository.DynamicRepository;
import com.gh.ghdg.businessMgr.bean.entities.Dynamic;
import com.gh.ghdg.businessMgr.service.DynamicService;
import com.gh.ghdg.common.commonVo.Page;
import com.gh.ghdg.common.commonVo.SearchFilter;
import com.gh.ghdg.common.utils.Result;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("business/dynamic")
public class DynamicController extends BaseMongoController<Dynamic, DynamicRepository, DynamicService> {
    @GetMapping("/dynamicList/{id}")
    public Result dynamicList(@PathVariable String id){
        return Result.suc("个人动态",service.getDynamicList(id));
    }
    
    @GetMapping("/page")
    public Result dynamicPage(@ModelAttribute Page page,
                                @RequestParam String bloggerId){
        Sort sort = Sort.by(Sort.Direction.DESC,"createdDate");
        page.setSort(sort);
        List<SearchFilter> searchFilterList = new ArrayList<>();
        SearchFilter searchFilter = SearchFilter.build("bloggerId", SearchFilter.Operator.EQ, bloggerId);
        searchFilterList.add(searchFilter);
        page.setFilters(searchFilterList);
    
        Page<Dynamic> dynamicPage = super.queryPage(page);
        return Result.suc(dynamicPage);
    }
    
    @PostMapping("/addDynamic")
    public void addDynamic(@RequestParam(required = false) String dynamicContent,
                           @RequestParam String action,
                           @RequestParam String blogId){
        service.addDynamic(blogId,action,dynamicContent);
    }
    
}
