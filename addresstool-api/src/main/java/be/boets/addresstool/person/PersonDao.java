package be.boets.addresstool.person;

import be.boets.addresstool.search.SearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

public interface PersonDao {

    List<PersonEntity> getAll();
    Page<PersonEntity> getAll(PageRequest pageRequest);
    Optional<PersonEntity> getById(int id);
    PersonEntity save(PersonEntity personEntity);
    void delete(PersonEntity personEntity);
    void deleteById(int id);
    void deleteAll();
    void update(PersonEntity personEntity);
    List<PersonEntity> search(SearchCriteria criteria);
}
