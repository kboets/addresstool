package be.boets.addresstool.address.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

/**
 * Alternative could be :
 * https://opendata.brussel.be/explore/dataset/codes-ins-nis-postaux-belgique/api/?disjunctive.postal_code&disjunctive.refnis_code&disjunctive.gemeentenaam&disjunctive.nom_commune&disjunctive.code_ins_region&disjunctive.region_fr&disjunctive.region_nl&disjunctive.region_en
 *
 *
 */
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

    public List<CityResponse> findByCityName(String cityName) {
        return restClient
                .get()
                .uri("/{postcode}.json", cityName)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }
}
