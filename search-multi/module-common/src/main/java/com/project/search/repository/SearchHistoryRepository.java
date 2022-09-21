package com.project.search.repository;

import com.project.search.domain.PopularSearch;
import com.project.search.domain.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {
    @Query(value = "SELECT sh.search_Word as searchWord, count(sh.search_Word) as searchCount " +
            "FROM Search_History sh " +
            "where sh.search_Date >= :searchDate " +
            "group by sh.search_Word " +
            "order by searchCount desc, searchWord ASC limit 10" , nativeQuery=true)
    List<PopularSearch> findTop10OrderBySearchCountDesc(LocalDateTime searchDate);
}