package be.boets.addresstool.journey;

import be.boets.addresstool.person.Person;
import be.boets.addresstool.search.SearchCriteria;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;

import static be.boets.addresstool.search.SearchCriteria.SearchCriteriaBuilder.aSearchCriteria;
import static org.assertj.core.api.Assertions.assertThat;


public class SearchIT extends AbstractTestConfig {

    @Autowired
    private RestTestClient restTestClient;


    @Test
    public void search_givenSearchCriteria_shouldReturnPerson() {
        SearchCriteria criteria = aSearchCriteria()
                .withFirstName("John")
                .withName("Doh")
                .build();

        Person[] responseBody = restTestClient.post()
                .uri("/api/search")
                .contentType(MediaType.APPLICATION_JSON)
                .body(criteria)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Person[].class)
                .returnResult()
                .getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody).hasSize(2);
        assertThat(responseBody[0].firstName()).isEqualTo("John");
        assertThat(responseBody[1].firstName()).isEqualTo("John");
    }

    @Test
    public void search_givenCityCriteria_shouldReturnPersonsFromCity() {
        SearchCriteria criteria = aSearchCriteria()
                .withCity("Pelt")
                .build();

        Person[] responseBody = restTestClient.post()
                .uri("/api/search")
                .contentType(MediaType.APPLICATION_JSON)
                .body(criteria)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Person[].class)
                .returnResult()
                .getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody).hasSize(2);
        assertThat(responseBody).extracting(Person::firstName).containsExactlyInAnyOrder("John", "Jane");
    }

    @Test
    public void search_givenCriteriaWithNoResults_shouldReturnEmptyList() {
        SearchCriteria criteria = aSearchCriteria()
                .withFirstName("NonExisting")
                .build();

        Person[] responseBody = restTestClient.post()
                .uri("/api/search")
                .contentType(MediaType.APPLICATION_JSON)
                .body(criteria)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Person[].class)
                .returnResult()
                .getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody).isEmpty();
    }

    @Test
    public void search_givenMultipleCriteria_shouldReturnMatchingPerson() {
        SearchCriteria criteria = aSearchCriteria()
                .withFirstName("John")
                .withCity("Averbode")
                .build();

        Person[] responseBody = restTestClient.post()
                .uri("/api/search")
                .contentType(MediaType.APPLICATION_JSON)
                .body(criteria)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Person[].class)
                .returnResult()
                .getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody).hasSize(1);
        assertThat(responseBody[0].firstName()).isEqualTo("John");
        assertThat(responseBody[0].lastName()).isEqualTo("Doh");
    }
}
