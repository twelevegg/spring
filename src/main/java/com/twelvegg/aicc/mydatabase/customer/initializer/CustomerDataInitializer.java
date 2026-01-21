package com.twelvegg.aicc.mydatabase.customer.initializer;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@Component
public class CustomerDataInitializer implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;
    private static final Logger logger = Logger.getLogger(CustomerDataInitializer.class.getName());

    public CustomerDataInitializer(@Qualifier("mysqlJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        insertDummyPlans();
        if (isTableEmpty("customers")) {
            insertDummyData();
        }
    }

    private void insertDummyPlans() {
        if (isTableEmpty("internet_plans")) {
            String sql = "INSERT INTO internet_plans (name, description, price, base_benefit) VALUES (?, ?, ?, ?)";
            List<Object[]> batchArgs = new ArrayList<>();
            for (int i = 1; i <= 5; i++) {
                batchArgs.add(new Object[] { "Internet Plan " + i, "Description " + i, "Price " + i, "Benefit " + i });
            }
            jdbcTemplate.batchUpdate(sql, batchArgs);
            logger.info("INIT: Inserted dummy internet plans.");
        }

        if (isTableEmpty("mobile_plans")) {
            String sql = "INSERT INTO mobile_plans (name, description, price, `condition`) VALUES (?, ?, ?, ?)";
            List<Object[]> batchArgs = new ArrayList<>();
            for (int i = 1; i <= 5; i++) {
                batchArgs.add(new Object[] { "Mobile Plan " + i, "Description " + i, "Price " + i, "Condition " + i });
            }
            jdbcTemplate.batchUpdate(sql, batchArgs);
            logger.info("INIT: Inserted dummy mobile plans.");
        }

        if (isTableEmpty("iptv_plans")) {
            String sql = "INSERT INTO iptv_plans (name, description, price_tv_only) VALUES (?, ?, ?)";
            List<Object[]> batchArgs = new ArrayList<>();
            for (int i = 1; i <= 5; i++) {
                batchArgs.add(new Object[] { "IPTV Plan " + i, "Description " + i, "Price " + i });
            }
            jdbcTemplate.batchUpdate(sql, batchArgs);
            logger.info("INIT: Inserted dummy iptv plans.");
        }

        if (isTableEmpty("bundle_products")) {
            String sql = "INSERT INTO bundle_products (name, description, `condition`) VALUES (?, ?, ?)";
            List<Object[]> batchArgs = new ArrayList<>();
            for (int i = 1; i <= 5; i++) {
                batchArgs.add(new Object[] { "Bundle Product " + i, "Description " + i, "Condition " + i });
            }
            jdbcTemplate.batchUpdate(sql, batchArgs);
            logger.info("INIT: Inserted dummy bundle products.");
        }
    }

    private boolean isTableEmpty(String tableName) {
        try {
            Integer count = jdbcTemplate.queryForObject("SELECT count(*) FROM " + tableName, Integer.class);
            return count != null && count == 0;
        } catch (Exception e) {
            return false;
        }
    }

    private void insertDummyData() {
        String sql = "INSERT INTO customers (" +
                "internet_plan_id, mobile_plan_id, iptv_plan_id, bundle_product_id, name, age, gender, phone_number, " +
                "is_foreigner, contract_period_months, remaining_contract_months, is_optional_contract, has_welfare_card, "
                +
                "overage_last_month_1, overage_last_month_2, is_data_carry_over, is_data_sharing, household_type, is_remote_work) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        List<Object[]> batchArgs = new ArrayList<>();
        Random random = new Random();

        for (int i = 1; i <= 20; i++) {
            batchArgs.add(new Object[] {
                    (long) random.nextInt(5) + 1,
                    (long) random.nextInt(5) + 1,
                    (long) random.nextInt(5) + 1,
                    (long) random.nextInt(5) + 1,
                    "Customer" + i,
                    String.valueOf(20 + random.nextInt(40)),
                    random.nextBoolean() ? "M" : "F",
                    "010-" + (1000 + random.nextInt(9000)) + "-" + (1000 + random.nextInt(9000)),
                    random.nextBoolean() ? "Y" : "N",
                    random.nextInt(36) + 1,
                    random.nextInt(12),
                    random.nextBoolean() ? "Y" : "N",
                    random.nextBoolean() ? "Y" : "N",
                    String.valueOf(random.nextInt(100)),
                    String.valueOf(random.nextInt(100)),
                    random.nextBoolean() ? "Y" : "N",
                    random.nextBoolean() ? "Y" : "N",
                    random.nextBoolean() ? "Single" : "Family",
                    random.nextBoolean() ? "Y" : "N"
            });
        }

        jdbcTemplate.batchUpdate(sql, batchArgs);
        logger.info("INIT: Inserted dummy customer data into the database.");
    }
}
