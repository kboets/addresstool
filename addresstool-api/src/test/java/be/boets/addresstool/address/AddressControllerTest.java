package be.boets.addresstool.address;

import be.boets.addresstool.address.client.StreetClientService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(AddressController.class)
@AutoConfigureRestTestClient
class AddressControllerTest {

    @Autowired
    RestTestClient restTestClient;

    @MockitoBean
    private AddressService addressService;
    @MockitoBean
    private StreetClientService streetClientService;

    @TestConfiguration
    static class TestCacheConfig {
        @Bean
        public CacheManager cacheManager() {
            return new ConcurrentMapCacheManager("cities", "streets", "addresstool");
        }
    }

    @Test
    @DisplayName( "GET /api/cityByPostalCode - should return all cities for the given zipcode")
    void findByPostalCode_givenValidPostalCode() {
        var cities = List.of(
                new City( "Hasselt", "3500", true),
                new City( "Wimmertingen", "3501", false),
                new City( "Kermt", "3510", false)
        );
        when(addressService.findByPostalCode(any())).thenReturn(cities);

        restTestClient.get().uri("/api/cityByPostalCode/3500").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$[0].name").isEqualTo("Hasselt");
    }

    @Test
    @DisplayName( "GET /api/cityByName - should return all cities for the given name")
    void cityByName_givenValidCityName() {
        var cities = List.of(
                new City("Averbode", "3271", false)
        );
        when(addressService.findByCityName(any())).thenReturn(cities);

        restTestClient.get().uri("/api/cityByName/Averbode").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$[0].name").isEqualTo("Averbode")
                .jsonPath("$[0].postalCode").isEqualTo("3271");
    }

    @Test
    @DisplayName( "GET /api/cityNamesByPostalCode - should return all city names for the given zipcode")
    void findCityNamesByPostalCode_givenValidPostalCode() {
        var cities = List.of(
                new City("Hasselt", "3500", true),
                new City( "Wimmertingen", "3501", false),
                new City( "Kermt", "3510", false)
        );
        when(addressService.findByPostalCode(any())).thenReturn(cities);

        restTestClient.get().uri("/api/cityNamesByPostalCode/3500").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$[0]").isEqualTo("Hasselt");
    }

    @Test
    @DisplayName( "GET /api/postalCodeByCityName - should return zipcode for the given city name")
    void findPostalCodeByCityName_givenValidCityName() {
        var cities = List.of(
                new City( "Averbode", "3271", false)
        );
        when(addressService.findByCityName(any())).thenReturn(cities);

        restTestClient.get().uri("/api/postalCodeByCityName/Averbode").exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isEqualTo("3271");
    }

    @Test
    @DisplayName( "GET /api/streetByPostalCode - should return all streets for the given zipcode")
    void findStreetByPostalCode_givenValidPostalCode() {
        when(streetClientService.findByPostalCode(any())).thenReturn(List.of("Averbodestraat", "Kroonstraat"));
        restTestClient.get().uri("/api/streetByPostalCode/3271").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$[0]").isEqualTo("Averbodestraat");
    }

    @Test
    @DisplayName( "GET /api/streetByCity - should return all streets for the given city name")
    void findStreetByCityName_givenValidCityName() {
        when(streetClientService.findByCityName(any())).thenReturn(List.of("Averbodestraat", "Kroonstraat"));
        restTestClient.get().uri("/api/streetByCity/Averbode").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$[0]").isEqualTo("Averbodestraat");
    }
}
