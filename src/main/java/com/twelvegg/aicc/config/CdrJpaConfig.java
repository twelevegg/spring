package com.twelvegg.aicc.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.twelvegg.aicc.cdr", entityManagerFactoryRef = "cdrEntityManagerFactory", transactionManagerRef = "cdrTransactionManager")
public class CdrJpaConfig {

    @Bean(name = "cdrEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean cdrEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("cdrDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.twelvegg.aicc.cdr")
                .persistenceUnit("cdr")
                .build();
    }

    @Bean(name = "cdrTransactionManager")
    public PlatformTransactionManager cdrTransactionManager(
            @Qualifier("cdrEntityManagerFactory") LocalContainerEntityManagerFactoryBean cdrEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(cdrEntityManagerFactory.getObject()));
    }
}
