package be.boets.addresstool.person;

import be.boets.addresstool.address.AddressMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface PersonMapper {
    PersonRecord toPersonRecord(Person person);
    Person toPerson(PersonRecord personRecord);
    List<PersonRecord> toPersonsRecord(List<Person> persons);
    List<Person> toPersons(List<PersonRecord> personRecords);
}
