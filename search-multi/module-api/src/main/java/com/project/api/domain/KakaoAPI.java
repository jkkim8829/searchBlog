package com.project.api.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@Configuration
@ConfigurationProperties("search.blog.kakao")
public class KakaoAPI {
    private String baseUrl;
    private String path;
    private String headerName;
    private String headerValue;
}