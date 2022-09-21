package com.project.blogsearch.search.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(indexes = @Index(name = "i_searchDate", columnList = "searchDate DESC"))
@NoArgsConstructor
@Getter
@Setter
public class SearchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long searchCode;
    @Column(length = 255, nullable = false)
    private String searchWord;
    @Column
    private LocalDateTime searchDate;

    @Builder // 빌더 패턴 클래스 생성, 생성자에 포함된 필드만 포함
    public SearchHistory(String searchWord, LocalDateTime searchDate) {
        this.searchWord = searchWord;
        this.searchDate = searchDate;
    }
}
