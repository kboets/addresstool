package be.boets.addresstool.address;

import be.boets.addresstool.address.client.CityClientService;
import be.boets.addresstool.address.client.CityResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressService {

    private final CityClientService cityClientService;

    public AddressService(CityClientService cityClientService) {
        this.cityClientService = cityClientService;
    }

    public List<CityRecord> findByZipCode(String postcode) {
        List<CityResponse> cityResponses = cityClientService.findByPostcode(postcode);
        return mapToCity(cityResponses);
    }

    public List<CityRecord> findByCityName(String cityName) {
        List<CityResponse> cityResponses = cityClientService.findByCityName(StringUtils.capitalize(cityName));
        return mapToCity(cityResponses);
    }

    private List<CityRecord> mapToCity(List<CityResponse> cityResponses) {
        List<CityRecord> cities = new ArrayList<>();
        for (CityResponse cityResponse : cityResponses) {
            CityRecord cityRecord = new CityRecord(cityResponse.postcode().name(), cityResponse.postcode().postalCode(), cityResponse.postcode().mainName().equals(cityResponse.postcode().name()));
            cities.add(cityRecord);
        }
        return cities;
    }
}

