package be.boets.addresstool.address;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class AddressService {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AddressService.class);
    private final CityRepository cityRepository;

    @Value("classpath:/data/zipcodes.xls")
    private Resource resource;

    public AddressService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> findByZipCode(String postcode) {
        List<CityEntity> cityList = cityRepository.findByPostalCode(postcode);
        return toCity(cityList);
    }

    public List<City> findByCityName(String cityName) {
        List<CityEntity> cityList = cityRepository.searchByName(cityName);
        return toCity(cityList);
    }

    public List<City> toCity(List<CityEntity> cityEntities) {
        List<City> cities = new ArrayList<>();
        for (CityEntity cityEntity : cityEntities) {
            cities.add(City.CityBuilder.aCity()
                    .withName(cityEntity.getName())
                    .withPostalCode(cityEntity.getPostalCode())
                    .withIsMain(cityEntity.isMain())
                    .build());
        }
        return cities;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    protected void loadCities() {
        if (cityRepository.count() == 0L) {
            LOGGER.info("Loading cities from excel file");
            List<CityEntity> loadedCityEntities = loadCities(resource);
            LOGGER.info("Loaded {} cities", loadedCityEntities.size());
            cityRepository.saveAll(loadedCityEntities);
        }
    }

    private List<CityEntity> loadCities(Resource resource) {
        List<CityEntity> cities = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(resource.getFile())) {
            HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
            // get the first sheet
            HSSFSheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                HSSFRow hssfRow = (HSSFRow) row;
                //skip the first row
                if (hssfRow.getRowNum() == 0) {
                    continue;
                }
                if (hssfRow.getCell(2) != null) {
                    int postCode = (int) hssfRow.getCell(0).getNumericCellValue();
                    CityEntity city =
                            new CityEntity(hssfRow.getCell(1).getStringCellValue(),
                                    String.valueOf(postCode),
                                    "Neen".equals(hssfRow.getCell(2).getStringCellValue()));
                    cities.add(city);
                }
            }
            return cities;
        } catch (Exception e) {
            LOGGER.error("Could not loading cities: {}", e.getMessage());
            return new ArrayList<>();
        }
    }


}

