package be.boets.addresstool.address;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class AddressController {


    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/cityByPostalCode/{postcode}")
    public ResponseEntity<List<City>> findByPostalCode(@PathVariable String postcode) {
        return ResponseEntity.ok(addressService.findByZipCode(postcode));
    }

    @GetMapping("/cityNamesByPostalCode/{postcode}")
    public ResponseEntity<List<String>> findCityNamesByPostalCode(@PathVariable String postcode) {
        List<City> cities = addressService.findByZipCode(postcode);
        Set<String> cityNames = new HashSet<>(cities.stream().map(City::name).toList());
        return ResponseEntity.ok(new ArrayList<>(cityNames));
    }

}
