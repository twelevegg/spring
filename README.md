# AICC Backend Service (Spring Boot)

## 📌 Introduction (프로젝트 소개)
**AI Contact Center (AICC) 플랫폼의 백엔드 서비스**입니다.  
고객 상담 내역(Call), 녹취록(Transcript), AI 분석 결과(Summary, Sentiment)를 관리하며, 상담원(Member)과 고객(Customer), 상품(Product) 정보를 통합 관리하는 REST API 서버입니다.

이 서비스는 **Spring Boot** 기반으로 구축되었으며, 상담 후처리와 실시간 분석 데이터를 저장하고 대시보드 및 관리자 페이지에 필요한 데이터를 제공합니다.

> **Note**: `.env` 파일에 `SPRING_API_KEY`를 설정해야 합니다. (노션 참고)

---

## � Tech Stack (기술 스택)
- **Language**: Java 17
- **Framework**: Spring Boot 3.x
- **Database**: MySQL / PostgreSQL (JPA/Hibernate)
- **Security**: Spring Security + JWT (JSON Web Token)
- **Storage**: AWS S3 (녹취 파일 및 데이터 저장)
- **API Docs**: Swagger (SpringDoc OpenAPI)
- **External Integration**: FastAPI (Python AI Server) - AI 분석 및 LLM 연동

---

## 🔑 Key Features (핵심 기능)

### 1. 상담 데이터 관리 (Call & Transcript Management)
- 상담 통화(Call) 메타데이터 저장 (발신자, 수신자, 통화 시간, 비용 등)
- **STT(Speech-to-Text)** 결과인 대화 내용(Transcript) 저장 및 조회
- 통화 종료 후 AI가 분석한 **상담 요약(Post Call Summary)** 및 **핵심 키워드** 저장

### 2. AI 분석 결과 저장 (AI Analysis Storage)
- **감정 분석(Sentiment Analysis)**: 고객의 감정 상태 및 만족도 지수(CES, CSAT, NPS) 저장
- **실시간 분석(Realtime Analysis)**: 상담 중 발생한 특이사항 기록
- **특이 케이스 감지(Abnormal/Violence Detection)**: 폭언, 욕설 등 민원성 패턴 감지 및 저장

### 3. 상품 및 고객 관리 (Product & Customer Management)
- **상품(Product)**: 인터넷(Internet), IPTV, 모바일(Mobile) 요금제 정보 관리
- **고객(Customer)**: 고객 정보 및 상담 이력 관리
- **테넌트(Tenant) & 부서(Department)**: 멀티 테넌트 및 조직 구조 관리

### 4. 대시보드 및 통계 (Dashboard & Analytics)
- 상담원별 성과, 통화량, 민원 발생률 등 통계 데이터 제공
- 관리자(Admin) 및 상담원(Agent)용 대시보드 API

---

## 📊 Key Performance Indicators (KPIs)

본 서비스는 AICC 운영 효율성을 모니터링하기 위해 다양한 KPI 데이터를 실시간으로 집계하여 제공합니다.

### 📈 Summary Metrics (핵심 지표)
- **FCR (First Contact Resolution)**: 첫 번째 상담 해결률
- **NPS (Net Promoter Score)**: 순수 추천 고객 지수 (고객 충성도)
- **CES (Customer Effort Score)**: 고객 노력 점수 (상담 편의성)
- **CSAT (Customer Satisfaction Score)**: 고객 만족도 점수
- **Sentiment Score**: 상담 전체의 감정 분석 점수

### 📞 Call Performance (통화 품질 지표)
- **FRT (First Response Time)**: 최초 응답 시간
- **Blocked Call Rate**: 통화 차단율 (연결 실패율)
- **Abandonment Rate**: 상담 포기율 (대기 중 이탈)
- **Active Waiting Calls**: 현재 대기 중인 통화 수

### ⚙️ Operations (운영 효율성)
- **CPC (Cost Per Call)**: 통화당 비용
- **Call Arrival Rate**: 시간당 통화 유입률
- **Peak Time Traffic**: 피크 타임 트래픽
- **Avg Call Duration**: 평균 통화 시간
- **Avg Resolution Time**: 평균 해결 시간

### 👨‍💼 Agent Productivity (상담원 생산성)
- **Occupancy Rate**: 상담원 점유율 (통화 및 업무 시간 비율)
- **Adherence**: 스케줄 준수율
- **Calls Per Hour**: 시간당 처리 통화 건수
- **ASA (Average Speed of Answer)**: 평균 응답 속도
- **AHT (Average Handle Time)**: 평균 처리 시간 (통화 + 후처리)
- **ACW (After Call Work)**: 후처리 작업 시간 (상담 종료 후 정리 시간)

---

## ⚙️ Main Service Logic (주요 서비스 로직)

### 📞 Call Ingest (상담 데이터 수집)
- **통화 종료(Call End)** 시점에 녹취록, 요약문, 감정 점수 등을 한 번에 수신하여 저장합니다.
- `CallIngestService`를 통해 Call, Transcript, PostCallSummary, Customer 정보를 트랜잭션 단위로 처리합니다.

### 📝 Product Catalog (상품 카탈로그)
- 통신사 상품(인터넷, IPTV, 모바일)의 상세 정보를 조회하고 관리합니다.
- 상담원이 고객에게 적합한 상품을 추천할 수 있도록 데이터를 제공합니다.

### 🔐 Authentication (인증/인가)
- **JWT** 기반의 인증 시스템을 사용하여 API 보안을 유지합니다.
- 사용자 역할(Role)에 따른 API 접근 제어를 수행합니다.

### 🗑️ 계정 탈퇴 전략: Hybrid Deletion Strategy (이중 삭제 아키텍처)
본 프로젝트는 **데이터의 가치보존(Data Preservation)**과 **개인정보 보호(Privacy Compliance)**를 동시에 만족하기 위해 **Hybrid Deletion (Soft + Hard Delete) 아키텍처**를 적용했습니다.

#### A. Soft Delete (비즈니스 데이터 보존)
- **대상**: `Member` (상담원)
- **방식**: `Status`를 `RESIGNED`로 변경하고 개인정보(이메일, 이름 등)를 **난수화(익명화)** 처리하여 '잊혀질 권리'를 보장하면서도 상담 이력은 유지합니다.

#### B. Hard Delete (개인정보 파기)
- **대상**: `MemberMetric` (개인 성과 지표), `RefreshToken`
- **방식**: 물리 스토리지에서 즉시 **영구 삭제**하여 불필요한 개인 데이터 축적을 방지합니다.

> **Why?** 단순 `DELETE`는 상담 이력 소실을 야기하고, 단순 `UPDATE`는 개인정보 보호 위반 소지가 있습니다. Hybrid 방식은 **GDPR 준수**와 **통계 정확성**을 모두 확보합니다.

---

## 🌐 API URL & Documentation

서버가 실행되면 **Swagger UI**를 통해 전체 API 명세를 확인할 수 있습니다.

- **Swagger UI**: `https://api.csnavigator.cloud/swagger-ui/index.html`
- **API Specs**: `https://api.csnavigator.cloud/v3/api-docs`

### Key Endpoints Example:
| Method | URI | Description |
|--------|-----|-------------|
| `POST` | `/api/v1/calls/end` | 통화 종료 후 상담 데이터(녹취, 요약, 점수) 저장 |
| `GET` | `/api/v1/products/mobile` | 모바일 상품 목록 조회 |
| `GET` | `/api/v1/customers/{id}` | 특정 고객 정보 및 이력 조회 |
| `POST` | `/api/v1/auth/login` | 로그인 및 토큰 발급 |