package com.gh.ghdg.elastic.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface SearchService {
    Object searchBlogger(String keyword, Integer curPage, Integer size) throws IOException;
    Object searchBlog(String keyword,Integer curPage, Integer size) throws IOException;
}
