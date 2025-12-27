package be.boets.addresstool.address.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class CityClientService {

    private final RestClient restClient;

    public CityClientService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.baseUrl("https://opzoeken-postcode.be").build();
    }

    public List<CityResponse> findByPostcode(String postcode) {
        return restClient
                .get()
                .uri("/{postcode}.json", postcode)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }
}
