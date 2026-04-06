package be.boets.addresstool.journey;

import be.boets.addresstool.person.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PersonIT extends AbstractTestConfig{

    @Autowired
    private RestTestClient restTestClient;

    @Test
    void getAllPersons_givenPersonsInDb_shouldReturnPersons() {
        Person[] responseBody = restTestClient.get()
                .uri("/api/person/all")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Person[].class)
                .returnResult()
                .getResponseBody();
        assertThat(responseBody).isNotEmpty();
        assertThat(responseBody).hasSize(4);
    }
}
