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
@ConfigurationProperties("search.blog.naver")
public class NaverAPI {
    private String baseUrl;
    private String path;
    private String headerId;
    private String headerSecret;
    private String headerIdValue;
    private String headerSecretValue;
}
