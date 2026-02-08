package be.boets.addresstool.person;

import be.boets.addresstool.search.SearchCriteria;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final PersonDao personDao;
    private final PersonMapper personMapper;

    public PersonService(PersonDao personDao, PersonMapper personMapper) {
        this.personMapper = personMapper;
        this.personDao = personDao;
    }

    public void save(PersonRecord person) {
        Person checkedPerson = upperFirstCharacter(personMapper.toPerson(person));
        if (checkedPerson.getId() == null) {
            personDao.save(upperFirstCharacter(checkedPerson));
        } else{
            personDao.update(checkedPerson);
        }
    }

    public void delete(PersonRecord person) {
        personDao.delete(personMapper.toPerson(person));
    }

    public void deleteById(int id) {
        personDao.deleteById(id);
    }

    public Person getById(int id) {
        return personDao.getById(id).orElse(null);
    }

    public List<PersonRecord> getAllPersons() {
        return personMapper.toPersonsRecord(personDao.getAll());
    }

    public List<PersonRecord> search(SearchCriteria searchCriteria) {
        return personMapper.toPersonsRecord(personDao.search(searchCriteria));
    }

    private Person upperFirstCharacter(Person person) {
        // update first character to uppercase
        person.setFirstName(person.getFirstName().substring(0, 1).toUpperCase() + person.getFirstName().substring(1));
        person.setLastName(person.getLastName().substring(0, 1).toUpperCase() + person.getLastName().substring(1));
        person.getAddress().setStreet(person.getAddress().getStreet().substring(0, 1).toUpperCase() + person.getAddress().getStreet().substring(1));
        person.getAddress().getCity().setName(person.getAddress().getCity().getName().substring(0, 1).toUpperCase() + person.getAddress().getCity().getName().substring(1));


        return person;
    }
}
