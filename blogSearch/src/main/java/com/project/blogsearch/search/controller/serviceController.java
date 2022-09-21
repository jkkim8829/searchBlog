package com.project.blogsearch.search.controller;

import com.project.blogsearch.search.domain.PopularSearch;
import com.project.blogsearch.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class serviceController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/popularSearch")
    public List<PopularSearch> getPopularSearchWord(@RequestParam int date) {
        return searchService.getPopularSearchWord(date);
    }

    @GetMapping("/searchBlog")
    public String searchBlog(@RequestParam String query, String sort, String page) throws Exception {
        if(!StringUtils.hasText(query)){
//            throw new Exception("검색어가 없습니다.");
        }
        return searchService.searchBlog(query, sort, page);
    }
}
