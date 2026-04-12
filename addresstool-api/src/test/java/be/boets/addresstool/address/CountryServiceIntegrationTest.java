package be.boets.addresstool.address;

import be.boets.addresstool.SharedPostgressContainer;
import be.boets.addresstool.address.client.CountryClientService;
import be.boets.addresstool.address.client.CountryResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = "spring.main.allow-bean-definition-overriding=true")
@Import({CountryService.class})
@Testcontainers
public class CountryServiceIntegrationTest {

    @Container
    @ServiceConnection
    private static final SharedPostgressContainer POSTGRES_CONTAINER = SharedPostgressContainer.getInstance();

    @Autowired
    private CountryService countryService;
    @Autowired
    private CountryRepository countryRepository;
    @MockitoBean
    private CountryClientService countryClientService;
    @MockitoBean
    private CountryMapper countryMapper;


    @Test
    public void loadCountries() {
        CountryResponse belgianResponse = CountryResponse.CountryResponseBuilder
                .aCountryResponse()
                .withCca2("BE")
                .withIdd(new CountryResponse.Idd("+3", List.of("2")))
                .withFlags(new CountryResponse.Flags("https://flagcdn.com/w320/be.png", null, null))
                .withName(new CountryResponse.Name("Belgium", null, null))
                .build();
        when(countryClientService.findAllEuropean()).thenReturn(List.of(belgianResponse));
        // initial state
        assertThat(countryRepository.findAll().size()).isEqualTo(0);
        // when
        countryService.loadCountries();
        // then
        assertThat(countryRepository.findAll().size()).isGreaterThan(0);
    }
}
