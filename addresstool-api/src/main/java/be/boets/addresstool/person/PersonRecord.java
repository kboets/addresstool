package be.boets.addresstool.person;

import be.boets.addresstool.address.AddressRecord;

import java.time.LocalDate;

public record PersonRecord(Integer id, String firstName, String lastName, LocalDate birthDate, AddressRecord addressRecord) {

    public static final class PersonRecordBuilder {
        private Integer id;
        private String firstName;
        private String lastName;
        private LocalDate birthDate;
        private AddressRecord addressRecord;

        private PersonRecordBuilder() {
        }

        public static PersonRecordBuilder aPersonRecord() {
            return new PersonRecordBuilder();
        }

        public PersonRecordBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public PersonRecordBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public PersonRecordBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public PersonRecordBuilder withBirthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public PersonRecordBuilder withAddressRecord(AddressRecord addressRecord) {
            this.addressRecord = addressRecord;
            return this;
        }

        public PersonRecord build() {
            return new PersonRecord(id, firstName, lastName, birthDate, addressRecord);
        }
    }
}
