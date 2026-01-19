package com.twelvegg.aicc.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.jdbc.autoconfigure.JdbcConnectionDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    @Primary
    @Qualifier("mysqlDataSource")
    public DataSource mysqlDataSource(
            @Qualifier("jdbcConnectionDetailsForAiccMysql1") JdbcConnectionDetails connectionDetails) {
        com.zaxxer.hikari.HikariDataSource dataSource = DataSourceBuilder.create()
                .url(connectionDetails.getJdbcUrl())
                .username(connectionDetails.getUsername())
                .password(connectionDetails.getPassword())
                .driverClassName(connectionDetails.getDriverClassName())
                .type(com.zaxxer.hikari.HikariDataSource.class)
                .build();

        dataSource.setPoolName("HikariPool-MySQL");
        dataSource.setMaxLifetime(1800000); // 30 minutes
        dataSource.setConnectionTestQuery("SELECT 1");
        return dataSource;
    }

    @Bean(name = "mysqlJdbcTemplate")
    public org.springframework.jdbc.core.JdbcTemplate mysqlJdbcTemplate(
            @Qualifier("mysqlDataSource") DataSource dataSource) {
        return new org.springframework.jdbc.core.JdbcTemplate(dataSource);
    }

    @Bean
    @Qualifier("postgresDataSource")
    public DataSource postgresDataSource(
            @Qualifier("jdbcConnectionDetailsForAiccPostgres1") JdbcConnectionDetails connectionDetails) {
        com.zaxxer.hikari.HikariDataSource dataSource = DataSourceBuilder.create()
                .url(connectionDetails.getJdbcUrl())
                .username(connectionDetails.getUsername())
                .password(connectionDetails.getPassword())
                .driverClassName(connectionDetails.getDriverClassName())
                .type(com.zaxxer.hikari.HikariDataSource.class)
                .build();

        dataSource.setPoolName("HikariPool-Postgres");
        dataSource.setMaxLifetime(1800000); // 30 minutes
        dataSource.setConnectionTestQuery("SELECT 1");
        return dataSource;
    }
}
