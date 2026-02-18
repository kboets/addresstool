package be.boets.addresstool.person;

import be.boets.addresstool.address.AddressRecord;
import be.boets.addresstool.address.CityRecord;
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

    public void save(PersonRecord personRecord) {
        PersonRecord upperCasePerson = upperFirstCharacter(personRecord);
        Person person = personMapper.toPerson(upperCasePerson);
        if (person.getId() == null) {
            personDao.save(person);
        } else{
            personDao.update(person);
        }
    }

    public void delete(PersonRecord person) {
        personDao.delete(personMapper.toPerson(person));
    }

    public void deleteById(int id) {
        personDao.deleteById(id);
    }

    public Optional<PersonRecord> getById(int id) {
        Optional<Person> person = personDao.getById(id);
        return person.map(personMapper::toPersonRecord);
    }

    public List<PersonRecord> getAllPersons() {
        return personMapper.toPersonsRecord(personDao.getAll());
    }

    public List<PersonRecord> search(SearchCriteria searchCriteria) {
        return personMapper.toPersonsRecord(personDao.search(searchCriteria));
    }

    private PersonRecord upperFirstCharacter(PersonRecord personRecord) {
        CityRecord upperCaseCity = CityRecord.CityRecordBuilder.aCityRecord()
                .withIsMain(personRecord.addressRecord().cityRecord().isMain())
                .withName(StringUtils.capitalize(personRecord.addressRecord().cityRecord().name().toLowerCase()))
                .withPostalCode(personRecord.addressRecord().cityRecord().postalCode())
                .build();
        AddressRecord upperCaseAddress = AddressRecord.AddressRecordBuilder.anAddressRecord()
                .withBox(personRecord.addressRecord().box())
                .withNumber(personRecord.addressRecord().number())
                .withStreet(StringUtils.capitalize(personRecord.addressRecord().street().toLowerCase()))
                .withCityRecord(upperCaseCity)
                .build();
        return PersonRecord.PersonRecordBuilder
                .aPersonRecord()
                .withId(personRecord.id())
                .withBirthDate(personRecord.birthDate())
                .withFirstName(StringUtils.capitalize(personRecord.firstName().toLowerCase()))
                .withLastName(StringUtils.capitalize(personRecord.lastName().toLowerCase()))
                .withAddressRecord(upperCaseAddress)
                .build();
    }
}
