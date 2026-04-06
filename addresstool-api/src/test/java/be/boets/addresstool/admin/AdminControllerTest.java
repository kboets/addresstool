package be.boets.addresstool.admin;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.mockito.Mockito.when;

@WebMvcTest(AdminController.class)
@AutoConfigureRestTestClient
public class AdminControllerTest {

    @Autowired
    RestTestClient restTestClient;

    @MockitoBean
    private Environment environment;

    @Test
    @DisplayName( "GET /api/admin/currentVersion - should return correct maven version")
    void getCurrentVersion_shouldReturnMavenVersion() {
        when(environment.getProperty("spring.application.version")).thenReturn("1.0.0");

        restTestClient.get().uri("/api/admin/currentVersion").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBody()
                .jsonPath("$.mavenVersion").isEqualTo("1.0.0");

    }
}
