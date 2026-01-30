package com.twelvegg.aicc.mydatabase.caselibrary.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "case_library")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CaseLibrary {
    @Id
    @Column(length = 20)
    private String id;

    @Column(nullable = false)
    private String title;

    private LocalDate date;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "case_library_tags", joinColumns = @JoinColumn(name = "case_id"))
    @Column(name = "tag")
    @Default
    private List<String> tags = new ArrayList<>();

    @Column(columnDefinition = "TEXT", nullable = false)
    private String body;

    public void update(String title, String body, List<String> tags, LocalDate date) {
        this.title = title;
        this.body = body;
        this.tags = tags != null ? new ArrayList<>(tags) : new ArrayList<>();
        this.date = date;
    }
}
