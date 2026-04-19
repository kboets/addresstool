package be.boets.addresstool.address;

import be.boets.addresstool.address.client.CountryClientService;
import be.boets.addresstool.address.client.CountryResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CountryServiceTest {

    @Mock
    private CountryClientService countryClientService;
    @Mock
    private CountryRepository countryRepository;
    @InjectMocks
    private CountryService underTest;

    @Test
    public void loadCountries_givenNoCountries_shouldLoadCountries() {
        // when
        when(countryRepository.count()).thenReturn(0L);
        when(countryClientService.findAllEuropean()).thenReturn(getAllEuropeans());
        when(countryRepository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));

        underTest.loadCountries();

        ArgumentCaptor<List<CountryEntity>> captor = ArgumentCaptor.forClass(List.class);
        verify(countryRepository).saveAll(captor.capture());

        List<CountryEntity> savedEntities = captor.getValue();
        assertEquals(2, savedEntities.size());
        assertTrue(savedEntities.stream().anyMatch(country ->
                "Belgium".equals(country.getName())
                        && "BE".equals(country.getCountryCode())
                        && "+32".equals(country.getPhoneCode())));
        assertTrue(savedEntities.stream().anyMatch(country ->
                "Spain".equals(country.getName())
                        && "ES".equals(country.getCountryCode())
                        && "+34".equals(country.getPhoneCode())));

    }

    @Test
    public void loadCountries_givenCountries_shouldNotLoadCountries() {
        // when
        when(countryRepository.count()).thenReturn(1L);

        underTest.loadCountries();

        verify(countryClientService, never()).findAllEuropean();
        verify(countryRepository, never()).saveAll(anyList());

    }

    private List<CountryResponse> getAllEuropeans() {
        List<CountryResponse> getAllEuropeans = new ArrayList<>();
        CountryResponse spainResponse = CountryResponse.CountryResponseBuilder
                .aCountryResponse()
                .withCca2("ES")
                .withIdd(new CountryResponse.Idd("+3", List.of("4")))
                .withFlags(new CountryResponse.Flags("https://flagcdn.com/w320/es.png", null, null))
                .withName(new CountryResponse.Name("Spain", null, null))
                .build();
        CountryResponse belgianResponse = CountryResponse.CountryResponseBuilder
                .aCountryResponse()
                .withCca2("BE")
                .withIdd(new CountryResponse.Idd("+3", List.of("2")))
                .withFlags(new CountryResponse.Flags("https://flagcdn.com/w320/be.png", null, null))
                .withName(new CountryResponse.Name("Belgium", null, null))
                .build();
        getAllEuropeans.add(belgianResponse);
        getAllEuropeans.add(spainResponse);
        return getAllEuropeans;
    }
}
