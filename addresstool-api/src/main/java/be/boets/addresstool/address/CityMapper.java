package be.boets.addresstool.address;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CityMapper {

    City toCityRecord(AddressCityEntity addressCityEntity);
    AddressCityEntity toCity(City city);
    List<City> toCitiesRecord(List<AddressCityEntity> cities);
    List<AddressCityEntity> toCities(List<City> cities);
}
