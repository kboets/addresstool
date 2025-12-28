package be.boets.addresstool.address;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
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

    @Test
    @DisplayName( "GET /cityByPostalCode - should return all cities with zipcode")
    void findByPostalCode_givenValidPostalCode() {
        var cities = List.of(
                new City(null, "Hasselt", "3500", true),
                new City(null, "Wimmertingen", "3501", false),
                new City(null, "Kermt", "3510", false)
        );
        when(addressService.findByZipCode(any())).thenReturn(cities);

        restTestClient.get().uri("/cityByZipCode/3500").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$[0].name").isEqualTo("Hasselt");
    }

}
