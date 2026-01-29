package com.twelvegg.aicc.mydatabase.common.initializer;

import com.twelvegg.aicc.cdr.domain.Cdr;
import com.twelvegg.aicc.cdr.repository.CdrRepository;
import com.twelvegg.aicc.common.util.PasswordEncoder;
import com.twelvegg.aicc.mydatabase.call.domain.Call;
import com.twelvegg.aicc.mydatabase.call.domain.PostCallSummary;
import com.twelvegg.aicc.mydatabase.call.repository.CallRepository;
import com.twelvegg.aicc.mydatabase.call.repository.PostCallSummaryRepository;
import com.twelvegg.aicc.mydatabase.customer.domain.Customer;
import com.twelvegg.aicc.mydatabase.customer.repository.CustomerRepository;
import com.twelvegg.aicc.mydatabase.department.domain.Department;
import com.twelvegg.aicc.mydatabase.department.repository.DepartmentRepository;
import com.twelvegg.aicc.mydatabase.member.domain.Member;
import com.twelvegg.aicc.mydatabase.member.domain.MemberMetric;
import com.twelvegg.aicc.mydatabase.member.repository.MemberMetricRepository;
import com.twelvegg.aicc.mydatabase.member.repository.MemberRepository;
import com.twelvegg.aicc.mydatabase.tenant.domain.Tenant;
import com.twelvegg.aicc.mydatabase.tenant.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
public class KpiDummyDataInitializer implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final TenantRepository tenantRepository;
    private final DepartmentRepository departmentRepository;
    private final CallRepository callRepository;
    private final CdrRepository cdrRepository;
    private final PostCallSummaryRepository postCallSummaryRepository;
    private final CustomerRepository customerRepository;
    private final MemberMetricRepository memberMetricRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.profiles.active:default}")
    private String activeProfile;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (!"dev".equals(activeProfile)) {
            return;
        }

        if (memberRepository.count() >= 10) {
            log.info("KPI Dummy Data: Members already exist. Skipping initialization.");
            return;
        }

        log.info("KPI Dummy Data: Starting initialization...");

        // 1. Ensure Tenant
        Tenant tenant = tenantRepository.findByName("AICC_TENANT")
                .orElseGet(() -> tenantRepository.save(Tenant.builder()
                        .name("AICC_TENANT")
                        .status("ACTIVE")
                        .createdAt(LocalDateTime.now())
                        .build()));

        // 2. Ensure Department
        Department department = departmentRepository.findAll().stream().findFirst()
                .orElseGet(() -> departmentRepository.save(Department.builder()
                        .tenant(tenant)
                        .name("CS_Team")
                        .description("Customer Service Team")
                        .build()));

        // 3. Ensure Customers (Increased to 100 for better FCR/Repeat logic)
        List<Customer> customers = customerRepository.findAll();
        if (customers.size() < 100) {
            customers.addAll(createDummyCustomers(tenant, 100 - customers.size()));
        }

        Random random = new Random();

        // 4. Create Active Members
        for (int i = 1; i <= 10; i++) {
            createMemberWithData(i, "ACTIVE", tenant, department, customers, random);
        }

        // 5. Create Resigned Members (for Attrition Rate)
        for (int i = 11; i <= 12; i++) {
            createMemberWithData(i, "RESIGNED", tenant, department, customers, random);
        }

        // 6. Create Self-Service Calls (No Member)
        createSelfServiceCalls(tenant, customers, random);

        log.info("KPI Dummy Data: Initialization finished.");
    }

    private void createMemberWithData(int index, String status, Tenant tenant, Department department,
            List<Customer> customers, Random random) {
        String email = "agent" + index + "@aicc.com";

        // Diversify status if "ACTIVE" is passed (meaning not RESIGNED)
        // Diversify status if "ACTIVE" is passed (meaning not RESIGNED)
        String computedStatus = status;
        if ("ACTIVE".equals(status)) {
            int statusRoll = random.nextInt(100);
            if (statusRoll < 60) {
                computedStatus = "ACTIVE"; // 60% Ready/Active
            } else if (statusRoll < 80) {
                computedStatus = "ON_CALL"; // 20% on call
            } else {
                computedStatus = "AWAY"; // 20% Away
            }
        }
        final String finalStatus = computedStatus;

        Member member = memberRepository.findByEmail(email)
                .orElseGet(() -> memberRepository.save(Member.builder()
                        .tenant(tenant)
                        .department(department)
                        .email(email)
                        .name("Agent " + index)
                        .password(passwordEncoder.encode("password"))
                        .role("USER")
                        .status(finalStatus)
                        .hireDate(LocalDate.now().minusDays(random.nextInt(365)))
                        .shiftType("DAY")
                        .workStartTime(LocalTime.of(9, 0))
                        .workEndTime(LocalTime.of(18, 0))
                        .build()));

        // Create Calls for this Member
        createCallsForMember(member, tenant, customers, random);

        // Create MemberMetric
        createMemberMetric(member, random);
    }

    private List<Customer> createDummyCustomers(Tenant tenant, int count) {
        List<Customer> newCustomers = new ArrayList<>();
        long currentSize = customerRepository.count();
        for (int i = 1; i <= count; i++) {
            newCustomers.add(customerRepository.save(Customer.builder()
                    .tenant(tenant)
                    .name("Dummy Customer " + (currentSize + i))
                    .phoneNumber("010-" + (1000 + i) + "-" + (1000 + i))
                    .build()));
        }
        return newCustomers;
    }

    private void createCallsForMember(Member member, Tenant tenant, List<Customer> customers, Random random) {
        // 30 calls per member
        for (int j = 0; j < 30; j++) {
            // Pick rand customer
            Customer customer = customers.get(random.nextInt(customers.size()));
            LocalDateTime callTime = LocalDateTime.now().minusDays(random.nextInt(30)).minusHours(random.nextInt(24));

            // Determine Call Type/Disposition
            // 85% Answered (Normal), 5% Active (In Progress), 5% Busy, 5% Failed/NoAnswer
            int typeRoll = random.nextInt(100);
            String disposition = "ANSWERED";
            boolean isActive = false;
            boolean isFailed = false;

            if (typeRoll < 85) {
                disposition = "ANSWERED";
            } else if (typeRoll < 90) {
                disposition = "ANSWERED"; // Technically answered but not finished
                isActive = true;
                // Active calls should be very recent
                callTime = LocalDateTime.now().minusSeconds(random.nextInt(300));
            } else if (typeRoll < 95) {
                disposition = "BUSY";
                isFailed = true;
            } else {
                disposition = random.nextBoolean() ? "FAILED" : "NO ANSWER";
                isFailed = true;
            }

            Call call = callRepository.save(Call.builder()
                    .member(member)
                    .tenant(tenant)
                    .customer(customer)
                    .phoneNumber(customer.getPhoneNumber())
                    .transferCount(isFailed ? 0 : random.nextInt(3))
                    .estimatedCost(isFailed ? BigDecimal.ZERO : BigDecimal.valueOf(100 + random.nextInt(500)))
                    .build());

            // PostCallSummary: Only for successfully completed calls (not active, not
            // failed)
            if (!isActive && !isFailed) {
                postCallSummaryRepository.save(PostCallSummary.builder()
                        .call(call)
                        .summaryText("Dummy call summary " + j)
                        .cesScore(random.nextDouble() * 5.0) // 1-5
                        .csatScore(random.nextDouble() * 100.0) // 0-100
                        .npsScore(random.nextDouble() * 10.0) // 0-10
                        .sentimentScore(random.nextDouble() * 2.0 - 1.0) // -1.0 to 1.0
                        .keyword("dummy,test,call")
                        .createdAt(callTime)
                        .build());
            }

            // Create CDR
            createCdrForCall(call, random, callTime, disposition, isActive);
        }
    }

    private void createSelfServiceCalls(Tenant tenant, List<Customer> customers, Random random) {
        // Create ~50 Self Service Calls (Member = null)
        for (int i = 0; i < 50; i++) {
            Customer customer = customers.get(random.nextInt(customers.size()));
            LocalDateTime callTime = LocalDateTime.now().minusDays(random.nextInt(30));

            Call call = callRepository.save(Call.builder()
                    // member is null
                    .tenant(tenant)
                    .customer(customer)
                    .phoneNumber(customer.getPhoneNumber())
                    .transferCount(0)
                    .estimatedCost(BigDecimal.valueOf(10 + random.nextInt(50))) // cheaper?
                    .build());

            // CDR for self service
            createCdrForCall(call, random, callTime, "ANSWERED", false);
        }
    }

    private void createCdrForCall(Call call, Random random, LocalDateTime callTime, String disposition,
            boolean isActive) {
        int duration = 0;
        int waitTime = random.nextInt(60); // 0-60 secs wait

        if ("ANSWERED".equals(disposition)) {
            // duration 1 min to 10 mins
            duration = 60 + random.nextInt(600);
        } else {
            // Failed, Busy etc -> duration usually 0 or very small
            duration = 0;
        }

        LocalDateTime start = callTime;
        LocalDateTime answer = null;
        LocalDateTime end = null;

        if ("ANSWERED".equals(disposition)) {
            // For answer, start = time - duration - wait. answer = time - duration. end =
            // time (if finished)
            // But if isActive, end is null.
            // If isActive, callTime is "Start Time" effectively.
            if (isActive) {
                start = callTime;
                answer = callTime.plusSeconds(waitTime); // Answered recently
                end = null; // Still active
                duration = 0; // Or current duration? CDR usually updates duration at end. leave 0 or partial.
            } else {
                // Finished call
                // Let's say callTime is the END time
                end = callTime;
                answer = end.minusSeconds(duration);
                start = answer.minusSeconds(waitTime);
            }
        } else {
            // Failed/Busy
            // Start = callTime, End = callTime + small delay, Answer = null
            start = callTime;
            answer = null;
            end = callTime.plusSeconds(5); // 5 sec ringing then fail
        }

        cdrRepository.save(Cdr.builder()
                .id(call.getId())
                .src(call.getPhoneNumber())
                .dst("CustomerCenter")
                .start(start)
                .answer(answer)
                .end(end)
                .duration(duration + waitTime)
                .billsec(duration)
                .disposition(disposition)
                .linkedId("LINKED-" + call.getId())
                .build());
    }

    private void createMemberMetric(Member member, Random random) {
        // Assume these are cumulative or latest metrics
        long totalLogin = 28800 + random.nextInt(3600); // 8h - 9h
        long totalBreak = 3600 + random.nextInt(1800); // 1h - 1.5h
        // Talk time + Ready time should be <= (Login - Break)
        long effective = totalLogin - totalBreak;
        long totalTalk = (long) (effective * (0.5 + random.nextDouble() * 0.3)); // 50-80% of effective time
        long totalReady = effective - totalTalk - random.nextInt(100);

        memberMetricRepository.save(MemberMetric.builder()
                .member(member)
                .callCount(50) // Matches loop count roughly
                .stressScore(random.nextDouble() * 10.0)
                .burnoutRisk(random.nextDouble() * 100.0)
                .totalLoginTime(totalLogin)
                .totalBreakTime(totalBreak)
                .totalTalkTime(totalTalk)
                .totalReadyTime(totalReady)
                .scheduleAdherenceScore(80 + random.nextInt(20))
                .build());
    }
}
