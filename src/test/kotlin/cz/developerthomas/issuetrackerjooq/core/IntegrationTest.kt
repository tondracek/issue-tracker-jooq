package cz.developerthomas.issuetrackerjooq.core

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container

@SpringBootTest
@ActiveProfiles("test")
abstract class IntegrationTest {

    companion object {
        @Container
        @ServiceConnection
        @JvmStatic
        val postgres = PostgreSQLContainer("postgres:17")
            .withDatabaseName("issue_tracker")
            .withUsername("postgres")
            .withPassword("postgres")
    }
}