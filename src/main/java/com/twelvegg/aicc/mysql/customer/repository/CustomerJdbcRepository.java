package com.twelvegg.aicc.mysql.customer.repository;

import com.twelvegg.aicc.mysql.customer.domain.Customer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CustomerJdbcRepository implements CustomerRepository {

    private final JdbcTemplate jdbcTemplate;

    public CustomerJdbcRepository(@Qualifier("mysqlJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Customer> customerRowMapper = (rs, rowNum) -> Customer.builder()
            .id(rs.getLong("id"))
            .internetPlanId(rs.getLong("internet_plan_id"))
            .mobilePlanId(rs.getLong("mobile_plan_id"))
            .iptvPlanId(rs.getLong("iptv_plan_id"))
            .bundleProductId(rs.getLong("bundle_product_id"))
            .name(rs.getString("name"))
            .age(rs.getString("age"))
            .gender(rs.getString("gender"))
            .phoneNumber(rs.getString("phone_number"))
            .isForeigner(rs.getString("is_foreigner"))
            .contractPeriodMonths(rs.getInt("contract_period_months"))
            .remainingContractMonths(rs.getInt("remaining_contract_months"))
            .isOptionalContract(rs.getString("is_optional_contract"))
            .hasWelfareCard(rs.getString("has_welfare_card"))
            .overageLastMonth1(rs.getString("overage_last_month_1"))
            .overageLastMonth2(rs.getString("overage_last_month_2"))
            .isDataCarryOver(rs.getString("is_data_carry_over"))
            .isDataSharing(rs.getString("is_data_sharing"))
            .householdType(rs.getString("household_type"))
            .isRemoteWork(rs.getString("is_remote_work"))
            .build();

    @Override
    public Optional<Customer> findById(Long id) {
        List<Customer> result = jdbcTemplate.query("SELECT * FROM customer WHERE id = ?", customerRowMapper, id);
        return result.stream().findAny();
    }
}
