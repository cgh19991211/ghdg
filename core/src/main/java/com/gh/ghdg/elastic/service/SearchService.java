package com.gh.ghdg.elastic.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface SearchService {
    List<Map<String,Object>> searchBlogger(String keyword, int curPage) throws IOException;
}
