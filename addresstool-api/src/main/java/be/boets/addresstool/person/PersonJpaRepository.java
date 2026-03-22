package be.boets.addresstool.person;

import be.boets.addresstool.search.SearchCriteria;
import be.boets.addresstool.search.SearchSpecs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public List<PersonEntity> getAll() {
        return personRepository.findAll();
    }

    @Override
    public Page<PersonEntity> getAll(PageRequest pageRequest) {
        return personRepository.findAll(pageRequest);
    }

    @Override
    public Optional<PersonEntity> getById(int id) {
        return personRepository.findById(id);
    }

    @Override
    public PersonEntity save(PersonEntity personEntity) {
        return personRepository.save(personEntity);
    }

    @Override
    public void delete(PersonEntity personEntity) {
        personRepository.delete(personEntity);
    }

    @Override
    public void deleteById(int id) {
        personRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        personRepository.deleteAll();
    }

    @Override
    public void update(PersonEntity personEntity) {
        personRepository.save(personEntity);
    }

    @Override
    public List<PersonEntity> search(SearchCriteria searchCriteria) {
        Specification<PersonEntity> spec = SearchSpecs.searchByCriteria(searchCriteria);
        return personRepository.findAll(spec);
    }

}
