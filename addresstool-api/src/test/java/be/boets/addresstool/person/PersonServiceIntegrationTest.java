package be.boets.addresstool.person;

import be.boets.addresstool.SharedPostgressContainer;
import be.boets.addresstool.address.AddressEntity;
import be.boets.addresstool.address.AddressMapperImpl;
import be.boets.addresstool.address.CityEntity;
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
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

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
    private static final SharedPostgressContainer POSTGRES_CONTAINER = SharedPostgressContainer.getInstance();

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
        CityEntity cityEntity = CityEntity.CityEntityBuilder.aCityEntity()
                .withName("Averbode")
                .withPostalCode("3271").build();
        AddressEntity addressEntity = AddressEntity.AddressEntityBuilder.anAddressEntity()
                .withStreet("Springhaanstraat")
                .withNumber("72")
                .withCityEntity(cityEntity)
                .build();
        PersonEntity john = PersonEntity.PersonEntityBuilder.aPersonEntity()
                //.withId(100)
                .withFirstName("John")
                .withLastName("Doeh")
                .withBirthDate(LocalDate.of(1975, 2, 4))
                .withAddressEntity(addressEntity)
                .build();

        Person johnRecord = personMapper.toPersonRecord(john);
        underTest.save(johnRecord);
        List<Person> allPersons = underTest.getAllPersons();
        assertThat(allPersons).hasSize(5);
    }

    @Test
    void searchPersons_givenOneCriteria() {
        SearchCriteria criteria = SearchCriteria.
                SearchCriteriaBuilder.aSearchCriteria()
                .withCity("Pelt")
                .build();
        List<Person> persons = underTest.search(criteria);
        assertThat(persons).hasSize(2);
        criteria = SearchCriteria.
                SearchCriteriaBuilder.aSearchCriteria()
                .withName("Doh")
                .build();
        persons = underTest.search(criteria);
        assertThat(persons).hasSize(2);
    }

    @Test
    void searchPersons_givenTwoCriteria() {
        SearchCriteria criteria = SearchCriteria.
                SearchCriteriaBuilder.aSearchCriteria()
                .withCity("Pelt")
                .withName("Doh")
                .build();
        List<Person> persons = underTest.search(criteria);
        assertThat(persons).hasSize(1);
        assertThat(persons.getFirst().firstName()).isEqualTo("John");
    }

    @Test
    void searchPersons_givenThreeCriteria() {
        SearchCriteria criteria = SearchCriteria.
                SearchCriteriaBuilder.aSearchCriteria()
                .withPostalCode("3271")
                .withName("Doh")
                .withStreet("Demerstraat")
                .build();
        List<Person> persons = underTest.search(criteria);
        assertThat(persons).hasSize(1);
        assertThat(persons.getFirst().firstName()).isEqualTo("John");
    }


}
