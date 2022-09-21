package com.project.blogsearch.search.service;

import com.project.blogsearch.search.domain.PopularSearch;
import com.project.blogsearch.search.domain.SearchHistory;
import com.project.blogsearch.search.repository.SearchHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

    public void saveSearchWord(String searchWord) {
        searchHistoryRepository.save(
                SearchHistory.builder()
                        .searchWord(searchWord)
                        .searchDate(LocalDateTime.now())
                        .build()
        );
    }

    public List<PopularSearch> getPopularSearchWord(int date) {
        LocalDateTime serarchDate = LocalDateTime.now();
        if(date == 0){
            serarchDate = LocalDateTime.parse("2020-01-01T00:00:00.000");
        } else {
            serarchDate = serarchDate.minusDays(date);
        }
        List<PopularSearch> searchs = searchHistoryRepository.findTop10OrderBySearchCountDesc(serarchDate);
        return searchs;
    }

    public String searchBlog(String query, String sort, String page){
        return blogAPI(query, sort, page);
    }

    public String blogAPI(String query, String sort, String page){
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("query", query);
        params.add("sort", sort);
        params.add("page", page);

        Mono<String> mono = WebClient.builder().baseUrl("https://dapi.kakao.com")
                .build().get().uri(builder -> builder.path("/v2/search/blog")
                        .queryParams(params).build())
                .header("Authorization","KakaoAK 67ef8b7edf8d8fcd23d194981369e0c9")
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        //성공 시 검색어 저장
                        saveSearchWord(params.getFirst("query"));
                        return response.bodyToMono(String.class);
                    } else {
                        //API응답이 정상적이지 않을 경우 Naver api호출
//                        return response.bodyToMono(String.class);
                        return naverBlogAPI(query, sort, page);
                    }
                });
        return mono.block();
    }

    public Mono<String> naverBlogAPI(String query, String sort, String start){
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("query", query);
        //Naver API Param 값 처리(빈값 넘어가면 에러발생 체크)
        if(StringUtils.hasText(sort) && "accuracy".equals(sort)){
            params.add("sort", "sim");
        } else if (StringUtils.hasText(sort) && "recency".equals(sort)) {
            params.add("sort", "date");
        }
        if (StringUtils.hasText(start)){
            params.add("start", start);
        }

        Mono<String> mono = WebClient.builder().baseUrl("https://openapi.naver.com")
                .build().get().uri(builder -> builder.path("/v1/search/blog.json")
                        .queryParams(params).build())
                .headers(headers -> {
                    headers.add("X-Naver-Client-Id", "6zwoshV5RjusFZErM6pE");
                    headers.add("X-Naver-Client-Secret", "0mQyJvbUYT");
                })
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        //성공 시 검색어 저장
                        saveSearchWord(params.getFirst("query"));
                        return response.bodyToMono(String.class);
                    } else {
                        //에러처리
                        return response.bodyToMono(String.class);
                    }
                });
        return mono;
    }
}
