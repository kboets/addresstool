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

    public List<City> findByZipCode(String postcode) {
        List<CityResponse> cityResponses = cityClientService.findByPostcode(postcode);
        return mapToCity(cityResponses);
    }

    public List<City> findByCityName(String cityName) {
        List<CityResponse> cityResponses = cityClientService.findByCityName(StringUtils.capitalize(cityName));
        return mapToCity(cityResponses);
    }

    private List<City> mapToCity(List<CityResponse> cityResponses) {
        List<City> cities = new ArrayList<>();
        for (CityResponse cityResponse : cityResponses) {
            City city = new City(cityResponse.postcode().name(), cityResponse.postcode().postalCode(), cityResponse.postcode().mainName().equals(cityResponse.postcode().name()));
            cities.add(city);
        }
        return cities;
    }
}

