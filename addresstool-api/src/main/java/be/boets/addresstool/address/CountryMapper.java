package be.boets.addresstool.address;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CountryMapper {
    Country toCountryRecord(CountryEntity countryEntity);
    @Mapping(target = "id", ignore = true)
    CountryEntity toCountryEntity(Country country);
    List<CountryEntity> toCountryEntities(List<Country> countries);
    List<Country> toCountryRecords(List<CountryEntity> countryEntities);
}
