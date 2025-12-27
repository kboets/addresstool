package be.boets.addresstool.address;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController {


    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/cityByPostalCode/{postcode}")
    public ResponseEntity<List<City>> findByPostCode(@PathVariable String postcode) {
        return ResponseEntity.ok(addressService.findByZipCode(postcode));
    }

    @GetMapping("/cityNamesByPostalCode/{postcode}")
    public ResponseEntity<List<String>> findCityNamesByPostCode(@PathVariable String postcode) {
        List<City> cities = addressService.findByZipCode(postcode);
        return ResponseEntity.ok(cities.stream().map(City::name).toList());
    }

}
