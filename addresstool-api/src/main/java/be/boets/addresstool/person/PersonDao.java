package be.boets.addresstool.person;

import be.boets.addresstool.search.SearchCriteria;

import java.util.List;
import java.util.Optional;

public interface PersonDao {

    List<PersonEntity> getAll();
    Optional<PersonEntity> getById(int id);
    void save(PersonEntity personEntity);
    void delete(PersonEntity personEntity);
    void deleteById(int id);
    void deleteAll();
    void update(PersonEntity personEntity);
    List<PersonEntity> search(SearchCriteria criteria);
}
