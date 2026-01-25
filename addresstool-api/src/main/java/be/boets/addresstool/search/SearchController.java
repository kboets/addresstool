package be.boets.addresstool.search;

import be.boets.addresstool.person.Person;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping(value = "/search")
    public List<Person> search(@RequestBody SearchCriteria searchCriteria) {
        return searchService.search(searchCriteria);
    }
}
