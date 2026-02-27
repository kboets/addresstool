package be.boets.addresstool.person;

import be.boets.addresstool.address.Address;
import be.boets.addresstool.address.City;
import be.boets.addresstool.search.SearchCriteria;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonDao personDao;
    private final PersonMapper personMapper;

    public PersonService(PersonDao personDao, PersonMapper personMapper) {
        this.personMapper = personMapper;
        this.personDao = personDao;
    }

    public void save(Person person) {
        Person upperCasePerson = upperFirstCharacter(person);
        PersonEntity personEntity = personMapper.toPerson(upperCasePerson);
        if (personEntity.getId() == null) {
            personDao.save(personEntity);
        } else{
            personDao.update(personEntity);
        }
    }

    public void delete(Person person) {
        personDao.delete(personMapper.toPerson(person));
    }

    public void deleteById(int id) {
        personDao.deleteById(id);
    }

    public Optional<Person> getById(int id) {
        Optional<PersonEntity> person = personDao.getById(id);
        return person.map(personMapper::toPersonRecord);
    }

    public List<Person> getAllPersons() {
        return personMapper.toPersonsRecord(personDao.getAll());
    }

    public List<Person> search(SearchCriteria searchCriteria) {
        List<PersonEntity> searchResult = personDao.search(searchCriteria);
        return personMapper.toPersonsRecord(searchResult);
    }

    private Person upperFirstCharacter(Person person) {
        City upperCaseCity = City.CityBuilder.aCity()
                .withIsMain(person.address().city().isMain())
                .withName(StringUtils.capitalize(person.address().city().name().toLowerCase()))
                .withPostalCode(person.address().city().postalCode())
                .build();
        Address upperCaseAddress = Address.AddressBuilder.anAddress()
                .withBox(person.address().box())
                .withNumber(person.address().number())
                .withStreet(StringUtils.capitalize(person.address().street().toLowerCase()))
                .withCity(upperCaseCity)
                .build();
        return Person.PersonBuilder
                .aPerson()
                .withId(person.id())
                .withBirthDate(person.birthDate())
                .withFirstName(StringUtils.capitalize(person.firstName().toLowerCase()))
                .withLastName(StringUtils.capitalize(person.lastName().toLowerCase()))
                .withAddress(upperCaseAddress)
                .build();
    }
}
