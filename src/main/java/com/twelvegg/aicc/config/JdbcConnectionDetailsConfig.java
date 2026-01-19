package com.twelvegg.aicc.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.autoconfigure.JdbcConnectionDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JdbcConnectionDetailsConfig {

    @Value("${database.mysql.url}")
    private String mysqlUrl;

    @Value("${database.mysql.username}")
    private String mysqlUsername;

    @Value("${database.mysql.password}")
    private String mysqlPassword;

    @Value("${database.mysql.driver-class-name}")
    private String mysqlDriverClassName;

    @Value("${database.postgres.url}")
    private String postgresUrl;

    @Value("${database.postgres.username}")
    private String postgresUsername;

    @Value("${database.postgres.password}")
    private String postgresPassword;

    @Value("${database.postgres.driver-class-name}")
    private String postgresDriverClassName;

    @Bean
    @Qualifier("jdbcConnectionDetailsForAiccMysql1")
    public JdbcConnectionDetails mysqlJdbcConnectionDetails() {
        return new JdbcConnectionDetails() {
            @Override
            public String getJdbcUrl() {
                return mysqlUrl;
            }

            @Override
            public String getUsername() {
                return mysqlUsername;
            }

            @Override
            public String getPassword() {
                return mysqlPassword;
            }

            @Override
            public String getDriverClassName() {
                return mysqlDriverClassName;
            }
        };
    }

    @Bean
    @Qualifier("jdbcConnectionDetailsForAiccPostgres1")
    public JdbcConnectionDetails postgresJdbcConnectionDetails() {
        return new JdbcConnectionDetails() {
            @Override
            public String getJdbcUrl() {
                return postgresUrl;
            }

            @Override
            public String getUsername() {
                return postgresUsername;
            }

            @Override
            public String getPassword() {
                return postgresPassword;
            }

            @Override
            public String getDriverClassName() {
                return postgresDriverClassName;
            }
        };
    }
}
