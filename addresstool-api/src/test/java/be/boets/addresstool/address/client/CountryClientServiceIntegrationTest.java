package be.boets.addresstool.address.client;

import be.boets.addresstool.journey.AbstractTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class CountryClientServiceIntegrationTest extends AbstractTestConfig {
    @Autowired
    private CountryClientService countryClientService;

    @Test
    void findAll_European_shouldReturnCountries() {
        List<CountryResponse> countries = countryClientService.findAllEuropean();

        assertThat(countries).isNotNull();
        assertThat(countries).isNotEmpty();
    }

}
