package be.boets.addresstool.address;

import be.boets.addresstool.address.client.CountryClientService;
import be.boets.addresstool.address.client.CountryResponse;
import org.slf4j.Logger;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CountryService {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(CountryService.class);
    private final CountryClientService countryClientService;
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    public CountryService(CountryClientService countryClientService, CountryRepository countryRepository, CountryMapper countryMapper) {
        this.countryClientService = countryClientService;
        this.countryRepository = countryRepository;
        this.countryMapper = countryMapper;
    }

    @Cacheable(value = "addresstool", key = "'europeanPhoneCodes'")
    public List<Country> getAllEuropeanPhoneCodes() {
        return countryMapper.toCountryRecords(countryRepository.findAll(Sort.by("name").ascending()));
    }


    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    protected void loadCountries() {
        if (countryRepository.count() == 0L) {
            List<CountryResponse> allEuropeanData = countryClientService.findAllEuropean();
            List<CountryEntity> countries = allEuropeanData.stream()
                    .map(this::mapCountryResponse)
                    .toList();
            List<CountryEntity> countryEntities = countryRepository.saveAll(countries);
            LOGGER.info("Loaded countries {}", countryEntities.size());
        }
    }

    private CountryEntity mapCountryResponse(CountryResponse countryResponse) {
        return CountryEntity.CountryEntityBuilder.aCountryEntity()
                .withName(countryResponse.name().common())
                .withCountryCode(countryResponse.cca2())
                .withFlagUrl(countryResponse.flags().png())
                .withPhoneCode(countryResponse.idd().root() + countryResponse.idd().suffixes().getFirst())
                .build();
    }
}
