package com.twelvegg.aicc.mydatabase.customer.initializer;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

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
        executeSqlScriptIfTableEmpty("internet_plans", "sql/internet_plans_insert.sql");
        executeSqlScriptIfTableEmpty("mobile_plans", "sql/mobile_plans_insert.sql");
        executeSqlScriptIfTableEmpty("iptv_plans", "sql/iptv_plans_insert.sql");
        executeSqlScriptIfTableEmpty("bundle_products", "sql/bundle_products_insert.sql");
    }

    private void executeSqlScriptIfTableEmpty(String tableName, String scriptPath) {
        if (isTableEmpty(tableName)) {
            try {
                ResourceDatabasePopulator populator = new ResourceDatabasePopulator(new ClassPathResource(scriptPath));
                populator.setSqlScriptEncoding("UTF-8");
                populator.execute(jdbcTemplate.getDataSource());
                logger.info("INIT: Inserted data into " + tableName + " from " + scriptPath);
            } catch (Exception e) {
                logger.severe("INIT: Failed to insert data into " + tableName + " from " + scriptPath + ": "
                        + e.getMessage());
            }
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
