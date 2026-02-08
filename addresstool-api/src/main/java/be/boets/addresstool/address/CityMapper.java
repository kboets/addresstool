package be.boets.addresstool.address;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CityMapper {

    CityRecord toCityRecord(City city);
    City toCity(CityRecord cityRecord);
    List<CityRecord> toCitiesRecord(List<City> cities);
    List<City> toCities(List<CityRecord> cityRecords);
}
