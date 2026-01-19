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
        createTableIfNotExists();
        if (isTableEmpty()) {
            insertDummyData();
        }
    }

    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS customers (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "internet_plan_id BIGINT, " +
                "mobile_plan_id BIGINT, " +
                "iptv_plan_id BIGINT, " +
                "bundle_product_id BIGINT, " +
                "name VARCHAR(255), " +
                "age VARCHAR(10), " +
                "gender VARCHAR(10), " +
                "phone_number VARCHAR(20), " +
                "is_foreigner VARCHAR(5), " +
                "contract_period_months INT, " +
                "remaining_contract_months INT, " +
                "is_optional_contract VARCHAR(5), " +
                "has_welfare_card VARCHAR(5), " +
                "overage_last_month_1 VARCHAR(255), " +
                "overage_last_month_2 VARCHAR(255), " +
                "is_data_carry_over VARCHAR(5), " +
                "is_data_sharing VARCHAR(5), " +
                "household_type VARCHAR(50), " +
                "is_remote_work VARCHAR(5)" +
                ")";
        jdbcTemplate.execute(sql);
    }

    private boolean isTableEmpty() {
        Integer count = jdbcTemplate.queryForObject("SELECT count(*) FROM customer", Integer.class);
        return count != null && count == 0;
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
                    String.valueOf(20 + random.nextInt(40)), // Age 20-60
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
