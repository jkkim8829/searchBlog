package com.project.search.service;

import com.project.search.domain.PopularSearch;

import java.util.List;

public interface SearchService {
    void saveSearchWord(String searchWord);
    List<PopularSearch> getPopularSearchWord(int date);
}
