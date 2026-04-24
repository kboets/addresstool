package be.boets.addresstool.address;

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
    private CityRepository cityRepository;

    @InjectMocks
    private AddressService addressService;

    @Test
    void givenValidZipCode_findByZipCode_shouldReturnListOfCities() {
        // create a list of city responses
        List<CityEntity> cityEntities = List.of(new CityEntity("Hasselt", "3500", true), new CityEntity("Sint-Lambrechts-Herk", "3500", false));
        when(cityRepository.findByPostalCode(any())).thenReturn(cityEntities);
        List<City> cities = addressService.findByZipCode("3500");
        assertEquals(2, cities.size());
        assertThat(cities.getFirst()).isEqualTo(new City("Hasselt", "3500", true));
    }

    @Test
    void givenInvalidZipCode_findByZipCode_shouldReturnEmptyList() {
        //when(cityClientService.findByPostcode(any())).thenReturn(List.of());
        when(cityRepository.findByPostalCode(any())).thenReturn(List.of());
        List<City> cities = addressService.findByZipCode("AB3500");
        assertEquals(0, cities.size());
    }



}
