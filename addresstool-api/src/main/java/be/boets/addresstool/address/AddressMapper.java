package be.boets.addresstool.address;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = CityMapper.class)
public interface AddressMapper {
    AddressEntity toAddress(Address address);
    Address toAddressRecord(AddressEntity addressEntity);

    List<Address> toAddressesRecord(List<AddressEntity> addressEntities);

    List<AddressEntity> toAddresses(List<Address> addresses);
}
