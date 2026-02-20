package be.boets.addresstool.search;

import be.boets.addresstool.person.PersonRecord;
import be.boets.addresstool.person.PersonService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    private final PersonService personService;

    public SearchService(PersonService personService) {
        this.personService = personService;
    }

    public List<PersonRecord> search(SearchCriteria searchCriteria) {
        return personService.search(searchCriteria);
    }


}
