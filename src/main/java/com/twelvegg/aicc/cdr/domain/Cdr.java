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

    // 전화가 "걸린 순간부터 완전히 종료될 때까지”의 전체 시간
    private Integer duration;

    // 상담원이 전화를 받아서 통화가 시작된 순간부터, 끊을 때까지의 순수 통화 시간.
    private Integer billsec;

    // 통화 상태 (ANSWERED, NO ANSWER, BUSY, FAILED)
    private String disposition;
}
