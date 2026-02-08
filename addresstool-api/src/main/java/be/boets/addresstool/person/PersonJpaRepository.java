package be.boets.addresstool.person;

import be.boets.addresstool.search.SearchCriteria;
import be.boets.addresstool.search.SearchSpecs;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PersonJpaRepository implements PersonDao {

    private final PersonRepository personRepository;

    public PersonJpaRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<Person> getAll() {
        return personRepository.findAll();
    }

    @Override
    public Optional<Person> getById(int id) {
        return personRepository.findById(id);
    }

    @Override
    public void save(Person person) {
        personRepository.save(person);
    }

    @Override
    public void delete(Person person) {
        personRepository.delete(person);
    }

    @Override
    public void deleteById(int id) {
        personRepository.deleteById(id);
    }

    @Override
    public void update(Person person) {
        personRepository.save(person);
    }

    @Override
    public List<Person> search(SearchCriteria searchCriteria) {
        Specification<Person> spec = SearchSpecs.searchByCriteria(searchCriteria);
        return personRepository.findAll(spec);
    }

}
