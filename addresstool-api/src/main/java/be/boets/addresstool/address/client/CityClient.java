package be.boets.addresstool.address.client;

import be.boets.addresstool.address.City;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

//@HttpExchange(url = "https://opzoeken-postcode.be", accept = "application/json")
public interface CityClient {

    //@HttpExchange(url = "/{postcode}.json")
    List<CityResponse> findByPostcode(String postcode);
}
