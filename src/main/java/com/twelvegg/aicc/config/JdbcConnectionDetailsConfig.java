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

    @Value("${database.cdr.url}")
    private String cdrUrl;

    @Value("${database.cdr.username}")
    private String cdrUsername;

    @Value("${database.cdr.password}")
    private String cdrPassword;

    @Value("${database.cdr.driver-class-name}")
    private String cdrDriverClassName;

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
    @Qualifier("jdbcConnectionDetailsForAiccCdr1")
    public JdbcConnectionDetails cdrJdbcConnectionDetails() {
        return new JdbcConnectionDetails() {
            @Override
            public String getJdbcUrl() {
                return cdrUrl;
            }

            @Override
            public String getUsername() {
                return cdrUsername;
            }

            @Override
            public String getPassword() {
                return cdrPassword;
            }

            @Override
            public String getDriverClassName() {
                return cdrDriverClassName;
            }
        };
    }
}
