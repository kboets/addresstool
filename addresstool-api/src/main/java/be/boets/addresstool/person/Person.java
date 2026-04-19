package be.boets.addresstool.person;

import be.boets.addresstool.address.Address;

import java.time.LocalDate;

public record Person(Integer id, String firstName, String lastName, LocalDate birthDate, String phoneNumber, String email, Address address) {


    public static final class PersonBuilder {
        private Integer id;
        private String firstName;
        private String lastName;
        private LocalDate birthDate;
        private String phoneNumber;
        private String email;
        private Address address;

        private PersonBuilder() {
        }

        public static PersonBuilder aPerson() {
            return new PersonBuilder();
        }

        public PersonBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public PersonBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public PersonBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public PersonBuilder withBirthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public PersonBuilder withAddress(Address address) {
            this.address = address;
            return this;
        }

        public PersonBuilder withPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public PersonBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Person build() {
            return new Person(id, firstName, lastName, birthDate, phoneNumber, email, address);
        }
    }
}
