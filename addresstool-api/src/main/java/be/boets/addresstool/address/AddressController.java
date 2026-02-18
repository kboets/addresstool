package be.boets.addresstool.address;

import be.boets.addresstool.address.client.StreetClientService;
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
    private final StreetClientService streetClientService;

    public AddressController(AddressService addressService, StreetClientService streetClientService) {
        this.addressService = addressService;
        this.streetClientService = streetClientService;
    }

    @GetMapping("/cityByPostalCode/{postcode}")
    public ResponseEntity<List<CityRecord>> findByPostalCode(@PathVariable String postcode) {
        return ResponseEntity.ok(addressService.findByZipCode(postcode));
    }

    @GetMapping("/cityByName/{cityName}")
    public ResponseEntity<List<CityRecord>> findByCityName(@PathVariable String cityName) {
        return ResponseEntity.ok(addressService.findByCityName(cityName));
    }

    @GetMapping("/cityNamesByPostalCode/{postalCode}")
    public ResponseEntity<List<String>> findCityNamesByPostalCode(@PathVariable String postalCode) {
        List<CityRecord> cities = addressService.findByZipCode(postalCode);
        Set<String> cityNames = new HashSet<>(cities.stream().map(CityRecord::name).toList());
        return ResponseEntity.ok(new ArrayList<>(cityNames));
    }

    @GetMapping("/postalCodeByCityName/{cityName}")
    public ResponseEntity<String> findPostalCodeByCityName(@PathVariable String cityName) {
        List<CityRecord> cities = addressService.findByCityName(cityName);
        for(CityRecord cityRecord : cities) {
            if (cityRecord.name().equalsIgnoreCase(cityName)) {
                return ResponseEntity.ok(cityRecord.postalCode());
            }
        }
        return ResponseEntity.ok(cities.getFirst().postalCode());
    }

    @GetMapping("/streetByPostalCode/{postalCode}")
    public ResponseEntity<List<String>> findStreetByPostalCode(@PathVariable String postalCode) {
        return ResponseEntity.ok(streetClientService.findByPostalCode(postalCode));
    }

    @GetMapping("/streetByCity/{city}")
    public ResponseEntity<List<String>> findStreetByCity(@PathVariable String city) {
        return ResponseEntity.ok(streetClientService.findByCityName(city));
    }

}
