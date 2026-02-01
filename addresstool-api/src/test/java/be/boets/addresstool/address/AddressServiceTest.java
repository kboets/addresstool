package be.boets.addresstool.address;

import be.boets.addresstool.address.client.CityClientService;
import be.boets.addresstool.address.client.CityResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @Mock
    private CityClientService cityClientService;

    @InjectMocks
    private AddressService addressService;

    @Test
    void givenValidZipCode_findByZipCode_shouldReturnListOfCities() {
        // create a list of city responses
        List<CityResponse> cityResponses = List.of(
                new CityResponse(
                        new CityResponse.PostcodeData("3500", "Hasselt", "Hasselt")
                ),
                new CityResponse(
                        new CityResponse.PostcodeData("3500", "Sint-Lambrechts-Herk", "Hasselt")
                )
        );
        when(cityClientService.findByPostcode(any())).thenReturn(cityResponses);
        List<CityRecord> cities = addressService.findByZipCode("3500");
        assertEquals(2, cities.size());
        assertThat(cities.getFirst()).isEqualTo(new CityRecord("Hasselt", "3500", true));
    }

    @Test
    void givenInvalidZipCode_findByZipCode_shouldReturnEmptyList() {
        when(cityClientService.findByPostcode(any())).thenReturn(List.of());
        List<CityRecord> cities = addressService.findByZipCode("AB3500");
        assertEquals(0, cities.size());
    }

    @Test
    void givenValidCity_findByCityName_shouldReturnListOfCities() {
        // create a list of city responses
        List<CityResponse> cityResponses = List.of(
                new CityResponse(
                        new CityResponse.PostcodeData("3271", "Averbode", "Scherpenheuvel-Zichem")
                )
        );
        when(cityClientService.findByCityName(any())).thenReturn(cityResponses);
        List<CityRecord> cities = addressService.findByCityName("averbode");
        assertEquals(1, cities.size());
        assertThat(cities.getFirst()).isEqualTo(new CityRecord( "Averbode", "3271", false));
    }


}
