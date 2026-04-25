package be.boets.addresstool.address;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.List;

import static org.mockito.Mockito.when;


@WebMvcTest(CountryController.class)
@AutoConfigureRestTestClient
class CountryControllerTest {

    @Autowired
    RestTestClient restTestClient;
    @MockitoBean
    private CountryService countryService;
    @MockitoBean
    private CountryMapper countryMapper;

    @TestConfiguration
    static class TestCacheConfig {
        @Bean
        public CacheManager cacheManager() {
            return new ConcurrentMapCacheManager("cities", "streets", "addresstool");
        }
    }


    @Test
    @DisplayName( "GET /api/getEuropeanCodes - should return all European countries with international phone codes")
    void getEuropeanCodes() {
        var countries = List.of(
                new Country("Belgium", "BE", "http://belgium.svg.png", "+32"),
                new Country("Spain", "ES",  "http://spain.svg.png", "+34"),
                new Country("France", "FR",  "http://france.svg.png", "+33")
        );
        when(countryService.getAllEuropeanPhoneCodes()).thenReturn(countries);

        restTestClient.get().uri("/api/getEuropeanCodes").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$[0].name").isEqualTo("Belgium")
                .jsonPath("$[0].phoneCode").isEqualTo("+32");
    }

}
