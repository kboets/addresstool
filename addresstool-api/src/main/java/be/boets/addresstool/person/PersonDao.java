package be.boets.addresstool.person;

import be.boets.addresstool.search.SearchCriteria;

import java.util.List;
import java.util.Optional;

public interface PersonDao {

    List<Person> getAll();
    Optional<Person> getById(int id);
    void save(Person person);
    void delete(Person person);
    void deleteById(int id);
    void deleteAll();
    void update(Person person);
    List<Person> search(SearchCriteria criteria);
}
