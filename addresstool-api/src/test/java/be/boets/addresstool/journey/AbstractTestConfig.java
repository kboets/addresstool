package be.boets.addresstool.journey;

import be.boets.addresstool.SharedPostgressContainer;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
@Testcontainers
@DirtiesContext( classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Sql("/db/testdata/insertData.sql")
public class AbstractTestConfig {

    @Container
    @ServiceConnection
    private static final SharedPostgressContainer POSTGRES_CONTAINER = SharedPostgressContainer.getInstance();
}
