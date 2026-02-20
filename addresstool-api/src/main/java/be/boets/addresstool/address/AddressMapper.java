package be.boets.addresstool.address;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = CityMapper.class)
public interface AddressMapper {
    @Mapping(source = "cityRecord", target = "city")
    AddressEntity toAddress(AddressRecord addressRecord);
    @Mapping(source = "city", target = "cityRecord")
    AddressRecord toAddressRecord(AddressEntity addressEntity);

    List<AddressRecord> toAddressesRecord(List<AddressEntity> addressEntities);

    List<AddressEntity> toAddresses(List<AddressRecord> addressRecords);
}
