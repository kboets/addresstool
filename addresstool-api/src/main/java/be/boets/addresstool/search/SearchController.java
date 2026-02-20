package be.boets.addresstool.search;

import be.boets.addresstool.person.PersonRecord;
import be.boets.addresstool.person.PersonService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SearchController {

    private final PersonService personService;

    public SearchController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping(value = "/search")
    public List<PersonRecord> search(@RequestBody SearchCriteria searchCriteria) {
        return personService.search(searchCriteria);
    }
}
