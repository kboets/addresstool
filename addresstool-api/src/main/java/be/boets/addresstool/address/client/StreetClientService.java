package be.boets.addresstool.address.client;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;

/**
 * Retrieving street info for each belgian city using following url:
 * https://www.schoolvoorbeeld.be/geocrowl/streets.php?postalcode=3270&street=&city=&search=1&output=json
 */
@Service
public class StreetClientService {

    private final RestClient restClient;
    private final JsonMapper jsonMapper;

    public StreetClientService(RestClient.Builder restClientBuilder, JsonMapper jsonMapper) {
        this.restClient = restClientBuilder.baseUrl("https://www.schoolvoorbeeld.be/geocrowl/streets.php?").build();
        this.jsonMapper = jsonMapper;
    }

    public List<String> findByPostalCode(String postalCode) {
        String response =  restClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("postalcode", postalCode)
                        .queryParam("street", "")
                        .queryParam("city", "")
                        .queryParam("output", "json")
                        .build())
                .retrieve()
                .body(String.class);
        return jsonMapper.readValue(response, List.class);
    }

    public List<String> findByCityName(String cityName) {
        String response =  restClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("postalcode", "")
                        .queryParam("street", "")
                        .queryParam("city", cityName)
                        .queryParam("output", "json")
                        .build())
                .retrieve()
                .body(String.class);
        return jsonMapper.readValue(response, List.class);
    }



}
