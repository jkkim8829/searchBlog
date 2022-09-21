package com.project.blogsearch.search.service;

import com.project.blogsearch.search.domain.PopularSearch;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

import java.util.List;

public interface SearchService {
    List<PopularSearch> getPopularSearchWord(int date);
    String searchBlog(String query, String sort, String page);
}
