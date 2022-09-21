package com.project.search.controller;

import com.project.search.domain.PopularSearch;
import com.project.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchController {
    @Autowired
    private SearchService searchService;

    @GetMapping("/popularSearch")
    public List<PopularSearch> getPopularSearchWord(@RequestParam int date) {
        return searchService.getPopularSearchWord(date);
    }
}
