package com.project.search.service;

import com.project.search.domain.PopularSearch;
import com.project.search.domain.SearchHistory;
import com.project.search.repository.SearchHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

    public void saveSearchWord(String searchWord) {
        //검색어 저장
        searchHistoryRepository.save(
                SearchHistory.builder()
                        .searchWord(searchWord)
                        .searchDate(LocalDateTime.now())
                        .build()
        );
    }

    public List<PopularSearch> getPopularSearchWord(int date) {
        //인기검색어 호출
        LocalDateTime serarchDate = LocalDateTime.now();
        if(date == 0){
            serarchDate = LocalDateTime.parse("2020-01-01T00:00:00.000");
        } else {
            serarchDate = serarchDate.minusDays(date);
        }
        //검색일 기준 date -하여 최근 인기검색어 수집
        List<PopularSearch> searchs = searchHistoryRepository.findTop10OrderBySearchCountDesc(serarchDate);
        return searchs;
    }
}
