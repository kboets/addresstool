package be.boets.addresstool.address;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CountryMapper {
    Country toCountryRecord(CountryEntity countryEntity);
    CountryEntity toCountryEntity(Country country);
    List<CountryEntity> toCountryEntities(List<Country> countries);
    List<Country> toCountryRecords(List<CountryEntity> countryEntities);
}
