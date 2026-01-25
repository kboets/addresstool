package be.boets.addresstool.person;

import be.boets.addresstool.address.Address;

import java.time.LocalDate;

public record Person(String firstName, String lastName, LocalDate birthDate, Address address) {
}
