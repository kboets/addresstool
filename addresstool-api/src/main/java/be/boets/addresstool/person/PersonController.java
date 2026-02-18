package be.boets.addresstool.person;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public void save(@RequestBody PersonRecord person) {
        personService.save(person);
    }

    @PutMapping
    public void update(@RequestBody PersonRecord person) {
        personService.save(person);
    }

    @DeleteMapping
    public void delete(@RequestBody PersonRecord person) {
        personService.delete(person);
    }

    @DeleteMapping({"/{id}"})
    public void deleteById(@PathVariable int id) {
        personService.deleteById(id);
    }

    @GetMapping("/all")
    public List<PersonRecord> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping("/{id}")
    public Optional<PersonRecord> getById(@PathVariable int id) {
        return personService.getById(id);
    }
}
