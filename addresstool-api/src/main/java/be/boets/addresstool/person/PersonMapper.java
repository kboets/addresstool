package be.boets.addresstool.person;

import be.boets.addresstool.address.AddressMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface PersonMapper {
    Person toPersonRecord(PersonEntity personEntity);
    PersonEntity toPerson(Person person);
    List<Person> toPersonsRecord(List<PersonEntity> persons);
    List<PersonEntity> toPersons(List<Person> people);
}
