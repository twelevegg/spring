package com.twelvegg.aicc.mydatabase.member.controller;

import com.twelvegg.aicc.mydatabase.member.dto.MemberResponseDto;
import com.twelvegg.aicc.mydatabase.member.dto.MemberCallStatsDto;
import com.twelvegg.aicc.mydatabase.call.dto.CallDetailResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
import com.twelvegg.aicc.mydatabase.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "상담원 관리", description = "상담원 정보 관리 API")
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "상담원 단건 조회", description = "ID로 상담원 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.findById(id));
    }

    @Operation(summary = "상담원 통화 통계 조회", description = "상담원의 오늘 통화 건수와 최근 통화 내역을 조회합니다.")
    @GetMapping("/{id}/stats")
    public ResponseEntity<MemberCallStatsDto> getCallStats(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getMemberCallStats(id));
    }

    @Operation(summary = "상담원 통화 이력 조회", description = "상담원의 통화 이력을 조회합니다. 날짜별 필터링이 가능합니다.")
    @GetMapping("/{id}/calls")
    public ResponseEntity<Page<CallDetailResponseDto>> getCallHistory(
            @PathVariable Long id,
            @RequestParam(required = false) LocalDate date,
            Pageable pageable) {
        return ResponseEntity.ok(memberService.getMemberCallHistory(id, date, pageable));
    }

    @Operation(summary = "상담원 통화 건수 조회", description = "상담원의 통화 건수를 조회합니다. 날짜별 필터링이 가능합니다.")
    @GetMapping("/{id}/calls/count")
    public ResponseEntity<Long> getCallCount(
            @PathVariable Long id,
            @RequestParam(required = false) LocalDate date) {
        return ResponseEntity.ok(memberService.getMemberCallCount(id, date));
    }
}
