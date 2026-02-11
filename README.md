# spring

.env에 SPRING_API_KEY를 설정해야 합니다. (노션 참고)

---

## 🔐 계정 탈퇴 전략 (Account Deletion Strategy)

본 프로젝트는 **데이터 무결성(Data Integrity)** 유지와 **개인정보 보호(Privacy)**, 그리고 **CRUD 요건 충족**을 동시에 만족시키기 위해 **Hybrid Deletion (이중 삭제)** 전략을 채택했습니다.

### 1. 전략 개요 (Hybrid Approach)
단순한 데이터 삭제(`DELETE`)는 연관된 상담 이력(`Call`)이나 지식 데이터(`CaseLibrary`)의 참조 무결성을 깨뜨릴 위험이 있습니다. 반면 단순한 상태 변경(`UPDATE`)만으로는 민감한 개인정보가 영구히 남을 수 있습니다.

따라서 우리는 **중요도와 민감도에 따라 데이터를 분류하여 두 가지 방식을 혼합**하여 적용했습니다.

### 2. 상세 구현 내용

#### A. Soft Delete (회원 기본 정보) - `UPDATE`
`Member` 엔티티는 상담 이력, 고객 관리 등 시스템 전반의 핵심 참조 데이터입니다. 이를 물리적으로 삭제할 경우, 과거 상담 로그나 통계 데이터가 고아(Orphan) 상태가 될 수 있습니다.

*   **처리 방식**:
    *   **Status 변경**: `ACTIVE` -> `RESIGNED` (로그인 및 접근 차단)
    *   **개인정보 익명화 (Anonymization)**:
        *   `email`: `deleted_{timestamp}_{original_email}` 형태로 난수화하여 변경. (동일 이메일 재가입 허용)
        *   `name`: "Unknown User"로 변경.
        *   `password`: 임의의 UUID로 덮어씌워 복구 불가능하게 파기.
*   **목적**: 회사의 자산인 업무 이력(Business Record)은 보존하되, 개인 식별 정보는 제거하여 개인정보 보호법 준수.

#### B. Hard Delete (개인 성과 지표) - `DELETE`
`MemberMetric`과 같이 순수하게 개인의 성과를 나타내는 데이터나, 보안상 중요한 `RefreshToken`은 물리적으로 완전히 삭제합니다.

*   **처리 방식**:
    *   `memberMetricRepository.deleteByMember(member)` 실행.
    *   `refreshTokenRepository.delete(...)` 실행.
*   **목적**: 불필요한 데이터 공간 확보 및 **확실한 삭제(DELETE) 기능 구현**을 통한 CRUD 완전성 확보.

### 3. 기대 효과
*   **기업 측면**: 상담 내역 및 업무 지식이 유실되지 않고 보존됨.
*   **사용자 측면**: 개인정보가 확실하게 파기되며, 원할 경우 즉시 재가입 가능.
*   **기술적 측면**: 참조 무결성 오류(FK Constraint Fail)를 방지하면서도 데이터베이스 용량 최적화.