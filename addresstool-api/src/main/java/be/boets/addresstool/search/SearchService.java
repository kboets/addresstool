package be.boets.addresstool.search;

import be.boets.addresstool.address.AddressRecord;
import be.boets.addresstool.address.CityRecord;
import be.boets.addresstool.person.PersonRecord;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SearchService {

    private final AddressRecord addressRecord;
    private final PersonRecord kurtPerson;
    private final PersonRecord elsPerson;

    public SearchService() {
        addressRecord = new AddressRecord("Bredestraat", 72, null, new CityRecord("Averbode", "3271", false));
        kurtPerson = new PersonRecord(null,"Kurt", "Boets", LocalDate.of(1974, 1, 16), addressRecord);
        elsPerson = new PersonRecord(null, "Els", "Aerts", LocalDate.of(1971, 5, 7), addressRecord);
    }

    public List<PersonRecord> search(SearchCriteria searchCriteria) {
        if (searchCriteria.city() != null) {
            return List.of(elsPerson, kurtPerson);
        }
        return List.of(kurtPerson);
    }


}
