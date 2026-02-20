package be.boets.addresstool.person;

import be.boets.addresstool.address.AddressEntity;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(
        name = "person",
        schema = "addresstool"
)
public class PersonEntity implements Serializable {
    @Id
    @SequenceGenerator(
            name = "person_id_seq",
            sequenceName = "person_id_seq",
            schema = "addresstool",
            allocationSize =  1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "person_id_seq")
    private Integer id;
    @Column(nullable = false)
    String firstName;
    @Column(nullable = false)
    String lastName;
    @Column(nullable = false)
    private LocalDate birthDate;
    @Embedded
    private AddressEntity addressEntity;

    public PersonEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public AddressEntity getAddress() {
        return addressEntity;
    }

    public void setAddress(AddressEntity addressEntity) {
        this.addressEntity = addressEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PersonEntity person = (PersonEntity) o;
        return Objects.equals(id, person.id) && Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(birthDate, person.birthDate) && Objects.equals(addressEntity, person.addressEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, birthDate, addressEntity);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", address=" + addressEntity +
                '}';
    }


    public static final class PersonEntityBuilder {
        private Integer id;
        private String firstName;
        private String lastName;
        private LocalDate birthDate;
        private AddressEntity addressEntity;

        private PersonEntityBuilder() {
        }

        public static PersonEntityBuilder aPersonEntity() {
            return new PersonEntityBuilder();
        }

        public PersonEntityBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public PersonEntityBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public PersonEntityBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public PersonEntityBuilder withBirthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public PersonEntityBuilder withAddressEntity(AddressEntity addressEntity) {
            this.addressEntity = addressEntity;
            return this;
        }

        public PersonEntity build() {
            PersonEntity personEntity = new PersonEntity();
            personEntity.setId(id);
            personEntity.setFirstName(firstName);
            personEntity.setLastName(lastName);
            personEntity.setBirthDate(birthDate);
            personEntity.addressEntity = this.addressEntity;
            return personEntity;
        }
    }
}

