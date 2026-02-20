package be.boets.addresstool.person;

import be.boets.addresstool.address.AddressMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface PersonMapper {
    @Mapping(source = "address", target = "addressRecord")
    PersonRecord toPersonRecord(PersonEntity personEntity);
    @Mapping(source = "addressRecord", target = "address")
    PersonEntity toPerson(PersonRecord personRecord);
    List<PersonRecord> toPersonsRecord(List<PersonEntity> persons);
    List<PersonEntity> toPersons(List<PersonRecord> personRecords);
}
