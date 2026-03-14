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
    public Person save(@RequestBody Person person) {
        return personService.save(person);
    }

    @PutMapping
    public void update(@RequestBody Person person) {
        personService.save(person);
    }

    @DeleteMapping
    public void delete(@RequestBody Person person) {
        personService.delete(person);
    }

    @DeleteMapping({"/{id}"})
    public void deleteById(@PathVariable int id) {
        personService.deleteById(id);
    }

    @GetMapping("/all")
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping("/{id}")
    public Optional<Person> getById(@PathVariable int id) {
        return personService.getById(id);
    }
}
