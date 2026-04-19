package be.boets.addresstool.address;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CountryController {

    private final CountryService countryService;
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/getEuropeanCodes")
    public ResponseEntity<List<Country>> getEuropeanCodes() {
        return ResponseEntity.ok(countryService.getAllEuropeanPhoneCodes());
    }

}
