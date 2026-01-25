package be.boets.addresstool.search;

import be.boets.addresstool.address.Address;
import be.boets.addresstool.address.City;
import be.boets.addresstool.person.Person;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SearchService {

    private final Address address;
    private final Person kurtPerson;
    private final Person elsPerson;

    public SearchService() {
        address = new Address("Bredestraat", 72, null, new City("Averbode", "3271", false));
        kurtPerson = new Person("Kurt", "Boets", LocalDate.of(1974, 1, 16), address);
        elsPerson = new Person("Els", "Aerts", LocalDate.of(1971, 5, 7), address);
    }

    public List<Person> search(SearchCriteria searchCriteria) {
        if (searchCriteria.city() != null) {
            return List.of(elsPerson, kurtPerson);
        }
        return List.of(kurtPerson);
    }


}
