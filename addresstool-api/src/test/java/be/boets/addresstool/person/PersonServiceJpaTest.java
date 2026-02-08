package be.boets.addresstool.person;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(PersonService.class)
class PersonServiceJpaTest {

    @Autowired
    private PersonDao personDao;
    @Autowired
    private PersonService underTest;
    @Autowired
    private PersonMapper personMapper;

    @BeforeEach
    void setUp() {

    }

    @Test
    void test() {

    }

}
