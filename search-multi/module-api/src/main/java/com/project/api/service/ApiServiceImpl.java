package com.project.api.service;

import com.project.api.domain.KakaoAPI;
import com.project.api.domain.NaverAPI;
import com.project.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ApiServiceImpl implements ApiService{
    @Autowired
    private SearchService searchService;

    //API 관련 값 정의
    private final KakaoAPI kakaoAPI;
    private final NaverAPI naverAPI;

    public ApiServiceImpl(KakaoAPI kakaoAPI, NaverAPI naverAPI) {
        this.kakaoAPI = kakaoAPI;
        this.naverAPI = naverAPI;
    }

    public String searchBlog(String query, String sort, String page){
        return kakaoBlogAPI(query, sort, page);
    }

    public String kakaoBlogAPI(String query, String sort, String page){
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("query", query);
        params.add("sort", sort);
        params.add("page", page);

        //WebClient 로 API호출
        Mono<String> mono = WebClient.builder().baseUrl(kakaoAPI.getBaseUrl())
                .build().get().uri(builder -> builder.path(kakaoAPI.getPath())
                        .queryParams(params).build())
                .header(kakaoAPI.getHeaderName(),kakaoAPI.getHeaderValue())
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        //성공 시 검색어 저장
                        searchService.saveSearchWord(params.getFirst("query"));
                        return response.bodyToMono(String.class);
                    } else {
                        //API응답이 정상적이지 않을 경우 Naver api호출
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

        //WebClient 로 API호출
        Mono<String> mono = WebClient.builder().baseUrl(naverAPI.getBaseUrl())
                .build().get().uri(builder -> builder.path(naverAPI.getPath())
                        .queryParams(params).build())
                .headers(headers -> {
                    headers.add(naverAPI.getHeaderId(), naverAPI.getHeaderIdValue());
                    headers.add(naverAPI.getHeaderSecret(), naverAPI.getHeaderSecretValue());
                })
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        //성공 시 검색어 저장
                        searchService.saveSearchWord(params.getFirst("query"));
                        return response.bodyToMono(String.class);
                    } else {
                        //에러리턴
                        return response.bodyToMono(String.class);
                    }
                });
        return mono;
    }
}
