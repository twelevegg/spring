# spring

.env에 SPRING_API_KEY를 설정해야 합니다. (노션 참고)

---

## 🔐 계정 탈퇴 전략: Hybrid Deletion Strategy (이중 삭제 아키텍처)

본 프로젝트는 단순한 데이터 삭제가 아닌, **데이터의 가치보존(Data Preservation)**과 **개인정보 보호(Privacy Compliance)**라는 상충되는 두 가치를 모두 만족시키기 위해 **Hybrid Deletion (Soft + Hard Delete) 아키텍처**를 설계 및 적용했습니다.

### 1. 전략 수립 배경 (Why Hybrid?)
일반적인 `DELETE` 연산은 연관된 모든 데이터(상담 이력, 분석 로그 등)를 연쇄적으로 삭제(`Cascade Delete`)하거나, 외래 키 제약조건(`FK Constraint`)으로 인해 삭제가 불가능한 문제를 야기합니다.
반대로, 단순한 `UPDATE` (Soft Delete)는 사용자의 개인정보가 DB에 영원히 남아있는 보안 리스크를 가집니다.

우리는 이 문제를 해결하기 위해 **"데이터의 성격에 따른 이원화 된 삭제 전략"**을 채택했습니다.

### 2. 세부 적용 기술 (Technical Implementation)

#### A. Soft Delete: 참조 무결성 및 비즈니스 데이터 보존
CRM 시스템의 핵심 자산인 **상담 이력(Call Logs)**과 **고객 응대 지식(Case Library)**은 회사의 소중한 자산입니다. 탈퇴한 직원이 수행한 업무 기록까지 삭제되는 것을 방지하기 위해 `Member` 엔티티는 **Soft Delete**를 적용했습니다.

*   **Logic**: `Member` 엔티티의 `Status`를 `RESIGNED`로 변경하여 즉시 로그인을 차단합니다.
*   **Privacy Guard (익명화 기술 적용)**: '잊혀질 권리'를 보장하기 위해 식별 가능한 개인정보(PII)를 완벽하게 파기합니다.
    *   `email`: `deleted_${timestamp}_${uuid}` 형태로 난수화 (단방향 해싱과 유사한 효과) → **동일 이메일로 즉시 재가입 가능**
    *   `password`: 무작위 UUID로 Overwrite → **계정 탈취 원천 봉쇄**
    *   `name`: "Unknown User"로 마스킹 처리

#### B. Hard Delete: ‘최소 저장 원칙’ 준수 및 리소스 최적화
불필요한 데이터 축적을 막고, 개인의 민감한 성과 지표는 영구히 파기하여 개인정보 보호 원칙을 강화했습니다.

*   **Target**: `MemberMetric` (개인별 상담 성과, 스트레스 지수 등), `RefreshToken` (보안 토큰)
*   **Action**: `deleteByMember(member)`를 통해 물리 스토리지에서 즉시 영구 삭제(Physically Deleted).
*   **Benefit**: 데이터베이스 스토리지 효율성 증대 및 잠재적인 개인정보 유출 리스크 0% 달성.

### 3. 도입 기대 효과 (Key Benefits)
| 구분 | 기존 방식 (Simple Delete) | **Hybrid Deletion (본 프로젝트)** |
| :--- | :--- | :--- |
| **데이터 무결성** | 연관 데이터(상담내역) 소실 위험 | **업무 이력 완벽 보존 (Orphan Data 방지)** |
| **개인정보 보호** | N/A (삭제 시 전부 삭제) | **개인정보 영구 파기 (익명화 + 물리 삭제)** |
| **재가입 편의성** | 동일 이메일 재사용 불가 이슈 발생 가능 | **즉시 재가입 가능 (Unique Constraint 해결)** |
| **법적 준수** | 애매함 | **GDPR 및 개인정보보호법 완벽 대응** |

> **"비즈니스 인사이트는 남기고, 개인의 흔적은 지운다."** 
> 이것이 우리가 정의한 계정 탈퇴의 핵심 철학입니다.

### 4. 실무형 아키텍처 비교 (Industry Best Practices)

단순한 구현을 넘어, 실제 엔터프라이즈 환경에서 고려되는 3가지 전략과 비교했을 때 본 프로젝트의 우수성은 다음과 같습니다.

| 전략 | 설명 | 활용 사례 | 본 프로젝트의 차별점 |
| :--- | :--- | :--- | :--- |
| **① Simple Soft Delete** | `status='DELETED'`만 변경 | 금융권/ERP (법적 보관 의무) | 개인정보 보호(GDPR) 취약점 해결 (난수화 적용) |
| **② Simple Hard Delete** | `DELETE WHERE id=?` | SNS/캐시 데이터 | Orphan Data(고아 데이터)로 인한 통계 왜곡 방지 |
| **③ Hybrid & Anonymization** | **참조 유지 + 정보 파기** | **쿠팡, 배민, Slack 등** | **현업에서 가장 권장되는 방식 채택** |

#### Why Enterprise-Grade? (현업 친화적 설계)
1.  **법적 리스크 회피 (GDPR Compliant)**: 물리적 삭제가 아니더라도, 이메일/비밀번호를 복구 불가능하게 난수화하여 '식별 불가능성'을 달성, 컴플라이언스를 준수했습니다.
2.  **비즈니스 연속성 (Business Continuity)**: 탈퇴한 유저의 상담 이력(`Call`)은 유지되므로, 회사의 자산인 데이터 통계와 분석(`MemberMetric` 제외)은 정확하게 유지됩니다.
3.  **대규모 트래픽 고려 (Scalability)**: 탈퇴 즉시 `Unique Index` (이메일) 충돌을 해소하여, 동시다발적인 탈퇴/재가입 트래픽에서도 DB 락(Lock) 없이 유연하게 동작합니다.