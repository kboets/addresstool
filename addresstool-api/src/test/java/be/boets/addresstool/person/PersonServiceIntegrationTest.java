package be.boets.addresstool.person;

import be.boets.addresstool.address.Address;
import be.boets.addresstool.address.AddressMapperImpl;
import be.boets.addresstool.address.City;
import be.boets.addresstool.address.CityMapperImpl;
import be.boets.addresstool.search.SearchCriteria;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Sql("/db/testdata/insertData.sql")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({PersonService.class,
        PersonMapperImpl.class,
        AddressMapperImpl.class,
        CityMapperImpl.class,
        PersonJpaRepository.class})
@Testcontainers
class PersonServiceIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:16-alpine"));

    @Autowired
    private PersonDao personDao;
    @Autowired
    private PersonService underTest;
    @Autowired
    private PersonMapper personMapper;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {}

    @Test
    void savePerson() {
        City city = City.CityBuilder.aCity()
                .withName("Averbode")
                .withPostalCode("3271").build();
        Address address = Address.AddressBuilder.anAddress()
                .withStreet("Springhaanstraat")
                .withNumber(72)
                .withCity(city)
                .build();
        Person john = Person.PersonBuilder.aPerson()
                //.withId(100)
                .withFirstName("John")
                .withLastName("Doeh")
                .withBirthDate(LocalDate.of(1975, 2, 4))
                .withAddress(address)
                .build();

        PersonRecord johnRecord = personMapper.toPersonRecord(john);
        underTest.save(johnRecord);
        List<PersonRecord> allPersons = underTest.getAllPersons();
        assertThat(allPersons).hasSize(5);
    }

    @Test
    void getAllPersons() {
        List<PersonRecord> allPersons = underTest.getAllPersons();
        assertThat(allPersons).hasSize(4);
    }

    @Test
    void searchPersons_givenOneCriteria() {
        SearchCriteria criteria = SearchCriteria.
                SearchCriteriaBuilder.aSearchCriteria()
                .withCity("Pelt")
                .build();
        List<PersonRecord> persons = underTest.search(criteria);
        assertThat(persons).hasSize(2);
        criteria = SearchCriteria.
                SearchCriteriaBuilder.aSearchCriteria()
                .withName("Doh")
                .build();
        persons = underTest.search(criteria);
        assertThat(persons).hasSize(2);
    }


}
