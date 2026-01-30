package com.twelvegg.aicc.mydatabase.caselibrary.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.twelvegg.aicc.mydatabase.member.domain.Member;

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
    @Column(name = "case_library_id", length = 20)
    private String caseLibraryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String title;

    private LocalDate date;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "case_library_tags", joinColumns = @JoinColumn(name = "case_library_id"))
    @Column(name = "tag")
    @Default
    private List<String> tags = new ArrayList<>();

    @Column(columnDefinition = "TEXT", nullable = false)
    private String body;

    public void update(Member member, String title, String body, List<String> tags, LocalDate date) {
        this.member = member;
        this.title = title;
        this.body = body;
        this.tags = tags != null ? new ArrayList<>(tags) : new ArrayList<>();
        this.date = date;
    }
}
