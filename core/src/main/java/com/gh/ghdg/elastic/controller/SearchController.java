package com.gh.ghdg.elastic.controller;

import com.gh.ghdg.common.utils.Result;
import com.gh.ghdg.elastic.service.SearchService;
import com.gh.ghdg.elastic.service.impl.SearchServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/search")
public class SearchController {
    @Resource
    private SearchService searchService;
    
    @GetMapping("/blogger")
    public Result searchBlogger(
            String keyword,
            @RequestParam(required = false, defaultValue = "1") Integer curPage,
            @RequestParam(required = false, defaultValue = "10")Integer pageSize) throws IOException {
        return Result.suc(searchService.searchBlogger(keyword,curPage,pageSize));
    }
    
    @GetMapping("/blog")
    public Result searchBlog(
            String keyword,
            @RequestParam(required = false, defaultValue = "1") Integer curPage,
            @RequestParam(required = false, defaultValue = "10")Integer pageSize) throws IOException {
        return Result.suc(searchService.searchBlog(keyword,curPage,pageSize));
    }
}
