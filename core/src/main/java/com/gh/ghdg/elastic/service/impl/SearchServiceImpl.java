package com.gh.ghdg.elastic.service.impl;

import com.gh.ghdg.elastic.service.SearchService;
import org.elasticsearch.action.get.GetRequest;
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
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class SearchServiceImpl implements SearchService {
    
    static final String BLOGGER_INFO = "ghdg.blogger_info";
    
    @Resource
    private RestHighLevelClient restHighLevelClient;
    
    public boolean indexIsExists(String index) throws IOException {
        GetIndexRequest request = new GetIndexRequest(index);
        boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        return exists;
    }
    
    @Override
    public List<Map<String,Object>> searchBlogger(String keyword, int curPage) throws IOException {
        if(curPage<=1)curPage = 1;
        List<Map<String,Object>> res = new ArrayList<>();
        //索引不存在则直接返回空集合
        if(!indexIsExists(BLOGGER_INFO)){
            return res;
        }
        //构建搜索请求
        SearchRequest searchRequest = new SearchRequest(BLOGGER_INFO);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
    
//        MatchPhraseQueryBuilder bloggerName = QueryBuilders.matchPhraseQuery("bloggerName", keyword);
        FuzzyQueryBuilder fuzzyQuery = QueryBuilders.fuzzyQuery("bloggerName", keyword);
//        TermQueryBuilder queryBuilder = QueryBuilders.termQuery("bloggerName", keyword);
//        MatchAllQueryBuilder allQueryBuilder = QueryBuilders.matchAllQuery();
        sourceBuilder.query(fuzzyQuery);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        
        //分页
        sourceBuilder.from(curPage);
//        sourceBuilder.size();
        
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
        for(SearchHit searchHit:response.getHits().getHits()){
            res.add(searchHit.getSourceAsMap());
        }
        
        return res;
    }
}
