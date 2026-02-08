package be.boets.addresstool.address;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = CityMapper.class)
public interface AddressMapper {
    AddressRecord toAddressRecord(Address address);
    Address toAddress(AddressRecord addressRecord);
    List<AddressRecord> toAddressesRecord(List<Address> addresses);
    List<Address> toAddresses(List<AddressRecord> addressRecords);
}
