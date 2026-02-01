package be.boets.addresstool.person;

import be.boets.addresstool.address.AddressRecord;

import java.time.LocalDate;

public record PersonRecord(String firstName, String lastName, LocalDate birthDate, AddressRecord addressRecord) {
}
