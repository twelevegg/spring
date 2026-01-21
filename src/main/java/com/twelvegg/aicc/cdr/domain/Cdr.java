package com.twelvegg.aicc.cdr.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "cdrs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Cdr {

    @Id
    private Long id;

    private String linkedId;

    private String src;

    private String dst;

    private LocalDateTime start;

    private LocalDateTime answer;

    private LocalDateTime end;

    private Integer duration;

    private Integer billsec;

    private String disposition;
}
