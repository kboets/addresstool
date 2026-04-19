package be.boets.addresstool.address.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

/**
 * Retrieving country and phone code info from:
 * https://restcountries.com/v3.1/region/Europe?fields=name,cca2,callingCodes,idd,flags
 */
@Service
public class CountryClientService {

    private final RestClient restClient;

    public CountryClientService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.baseUrl("https://restcountries.com/v3.1/region/Europe?fields=name,cca2,callingCodes,idd,flags").build();
    }

    public List<CountryResponse> findAllEuropean() {
        return restClient
                .get()
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }
}

