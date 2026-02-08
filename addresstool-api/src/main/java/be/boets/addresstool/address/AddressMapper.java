package be.boets.addresstool.address;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = CityMapper.class)
public interface AddressMapper {
    @Mapping(source = "cityRecord", target = "city")
    Address toAddress(AddressRecord addressRecord);
    @Mapping(source = "city", target = "cityRecord")
    AddressRecord toAddressRecord(Address address);

    List<AddressRecord> toAddressesRecord(List<Address> addresses);

    List<Address> toAddresses(List<AddressRecord> addressRecords);
}
