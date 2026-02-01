package be.boets.addresstool.address.client;

import java.util.List;

//@HttpExchange(url = "https://opzoeken-postcode.be", accept = "application/json")
public interface CityClient {

    //@HttpExchange(url = "/{postcode}.json")
    List<CityResponse> findByPostcode(String postcode);
}
