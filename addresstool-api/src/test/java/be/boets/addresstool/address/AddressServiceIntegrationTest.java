package be.boets.addresstool.address;

import be.boets.addresstool.SharedPostgressContainer;
import be.boets.addresstool.config.AddressToolConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({AddressService.class, AddressToolConfiguration.class, AddressToolConfiguration.class})
@Testcontainers
public class AddressServiceIntegrationTest {
    @Container
    @ServiceConnection
    private static final SharedPostgressContainer POSTGRES_CONTAINER = SharedPostgressContainer.getInstance();

    @Autowired
    private AddressService addressService;

    @Test
    void givenValidPostalCode_findByPostalCode_shouldReturnListOfCities() {
        List<City> cities = addressService.findByPostalCode("3500");
        assertThat(cities).isNotEmpty();
        assertThat(cities).hasSize(2);
        assertThat(cities.getFirst().name()).isEqualTo("Hasselt");
        assertThat(cities.getFirst().postalCode()).isEqualTo("3500");
    }

    @Test
    void givenInvalidPostalCode_findByPostalCode_shouldReturnEmptyList() {
        List<City> cities = addressService.findByPostalCode("AB3500");
        assertEquals(0, cities.size());
    }

    @Test
    void givenValidCityName_findByCityName_shouldReturnListOfCities() {
        List<City> cities = addressService.findByCityName("hasselt");
        assertEquals(3, cities.size());
        assertThat(cities.getFirst().name()).isEqualTo("Hasselt");
        assertThat(cities.getFirst().postalCode()).isEqualTo("3500");
    }
}
