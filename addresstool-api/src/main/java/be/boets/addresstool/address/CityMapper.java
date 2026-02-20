package be.boets.addresstool.address;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CityMapper {

    CityRecord toCityRecord(CityEntity cityEntity);
    CityEntity toCity(CityRecord cityRecord);
    List<CityRecord> toCitiesRecord(List<CityEntity> cities);
    List<CityEntity> toCities(List<CityRecord> cityRecords);
}
