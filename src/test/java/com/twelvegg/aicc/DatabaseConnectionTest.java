package com.twelvegg.aicc;

import com.twelvegg.aicc.cdr.repository.CdrRepository;
import com.twelvegg.aicc.mydatabase.call.repository.CallRepository;
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
    @Qualifier("cdrDataSource")
    private DataSource cdrDataSource;

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
    void testCdrConnection() throws Exception {
        assertNotNull(cdrDataSource, "CDR DataSource should not be null");
        try (Connection connection = cdrDataSource.getConnection()) {
            assertTrue(connection.isValid(5), "CDR connection should be valid");
            System.out.println("✅ CDR connection successful: " + connection.getMetaData().getURL());
        }
    }

    @Autowired
    private com.twelvegg.aicc.mydatabase.customer.repository.CustomerRepository customerRepository;

    @Test
    void testCustomerRepository() {
        assertNotNull(callRepository, "CallRepository should not be null");
        assertNotNull(cdrRepository, "CdrRepository should not be null");
        assertNotNull(customerRepository, "CustomerRepository should not be null");

        com.twelvegg.aicc.mydatabase.customer.domain.Customer customer = com.twelvegg.aicc.mydatabase.customer.domain.Customer
                .builder()
                .name("Test Customer")
                .build();

        customerRepository.save(customer);

        java.util.Optional<com.twelvegg.aicc.mydatabase.customer.domain.Customer> found = customerRepository
                .findById(customer.getId());
        assertTrue(found.isPresent(), "Customer should be found");
        assertEquals("Test Customer", found.get().getName());

        System.out.println("✅ CustomerRepository verification successful");
    }
}
