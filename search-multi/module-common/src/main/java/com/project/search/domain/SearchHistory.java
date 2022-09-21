package com.project.search.domain;

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

    @Builder
    public SearchHistory(String searchWord, LocalDateTime searchDate) {
        this.searchWord = searchWord;
        this.searchDate = searchDate;
    }
}
