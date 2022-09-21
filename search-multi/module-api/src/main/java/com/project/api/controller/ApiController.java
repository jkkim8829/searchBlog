package com.project.api.controller;

import com.project.api.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    @Autowired
    private ApiService apiService;

    @GetMapping("/searchBlog")
    public String searchBlog(@RequestParam String query, String sort, String page) throws Exception {
        if(!StringUtils.hasText(query)){
            throw new Exception("검색어가 없습니다.");
        }
        return apiService.searchBlog(query, sort, page);
    }
}
