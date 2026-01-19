package com.twelvegg.aicc;

import com.twelvegg.aicc.mysql.call.domain.Call;
import com.twelvegg.aicc.mysql.call.repository.CallRepository;
import com.twelvegg.aicc.postgres.cdr.domain.Cdr;
import com.twelvegg.aicc.postgres.cdr.repository.CdrRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DatabaseConnectionTest {

    @Autowired
    @Qualifier("mysqlDataSource")
    private DataSource mysqlDataSource;

    @Autowired
    @Qualifier("postgresDataSource")
    private DataSource postgresDataSource;

    @Autowired
    private CallRepository callRepository;

    @Autowired
    private CdrRepository cdrRepository;

    @Test
    void testMysqlConnection() throws Exception {
        assertNotNull(mysqlDataSource, "MySQL DataSource should not be null");
        try (Connection connection = mysqlDataSource.getConnection()) {
            assertTrue(connection.isValid(5), "MySQL connection should be valid");
            System.out.println("✅ MySQL connection successful: " + connection.getMetaData().getURL());
        }
    }

    @Test
    void testPostgresConnection() throws Exception {
        assertNotNull(postgresDataSource, "PostgreSQL DataSource should not be null");
        try (Connection connection = postgresDataSource.getConnection()) {
            assertTrue(connection.isValid(5), "PostgreSQL connection should be valid");
            System.out.println("✅ PostgreSQL connection successful: " + connection.getMetaData().getURL());
        }
    }
}
