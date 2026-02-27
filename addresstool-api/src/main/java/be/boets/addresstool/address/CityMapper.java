package be.boets.addresstool.address;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CityMapper {

    City toCityRecord(CityEntity cityEntity);
    CityEntity toCity(City city);
    List<City> toCitiesRecord(List<CityEntity> cities);
    List<CityEntity> toCities(List<City> cities);
}
