package com.gh.ghdg.elastic.service.impl;

import com.gh.ghdg.common.commonVo.Page;
import com.gh.ghdg.elastic.service.SearchService;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class SearchServiceImpl implements SearchService {
    
    static final String BLOGGER_INFO = "ghdg.blogger_info";
    static final String BLOG = "ghdg.blog";
    
    @Resource
    private RestHighLevelClient restHighLevelClient;
    
    public boolean indexIsExists(String index) throws IOException {
        GetIndexRequest request = new GetIndexRequest(index);
        boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        return exists;
    }
    
    @Override
    public Page searchBlogger(String keyword, Integer curPage,Integer size) throws IOException {
        if(curPage<=1)curPage = 1;
        //索引不存在则直接返回空集合
        if(!indexIsExists(BLOGGER_INFO)){
            return null;
        }
        //构建搜索请求
        SearchRequest searchRequest = new SearchRequest(BLOGGER_INFO);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
    
        FuzzyQueryBuilder fuzzyQuery = QueryBuilders.fuzzyQuery("bloggerName", keyword);
        sourceBuilder.query(fuzzyQuery);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        
        //分页
        sourceBuilder.from(curPage);
        sourceBuilder.size(size);

        //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("bloggerName");
        highlightBuilder.requireFieldMatch(false);
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        sourceBuilder.highlighter(highlightBuilder);
        
        //执行搜索
        searchRequest.source(sourceBuilder);
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
//        SearchHits hits = response.getHits();
        Page page = new Page(curPage,size);
        page.setTotal((int) response.getHits().getTotalHits().value);
        List<Map<String,Object>> records = new ArrayList<>();
        for (SearchHit hit : response.getHits().getHits()) {
            records.add(0,hit.getSourceAsMap());
        }
        page.setRecords(records);
        return page;
    }
    
    @Override
    public Page searchBlog(String keyword,Integer curPage,Integer size ) throws IOException{
        if(curPage<=1)curPage = 1;
        if(!indexIsExists(BLOG)){
            return null;
        }
        
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        MatchQueryBuilder content = QueryBuilders.matchQuery("content", keyword);
        MatchQueryBuilder title = QueryBuilders.matchQuery("title", keyword);
        boolQueryBuilder.should(content);
        boolQueryBuilder.should(title);
    
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(boolQueryBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
    
        //分页
        sourceBuilder.from(curPage);
        sourceBuilder.size(size);
    
        //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title");
        highlightBuilder.requireFieldMatch(false);
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        sourceBuilder.highlighter(highlightBuilder);
    
        //执行搜索
        SearchRequest searchRequest = new SearchRequest(BLOG);
        searchRequest.source(sourceBuilder);
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        Page page = new Page(curPage,size);
        page.setTotal((int) response.getHits().getTotalHits().value);
        List<Map<String,Object>> records = new ArrayList<>();
        for (SearchHit hit : response.getHits().getHits()) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            sourceAsMap.put("id",hit.getId());
            records.add(0,sourceAsMap);
        }
        page.setRecords(records);
        return page;
    }
}
