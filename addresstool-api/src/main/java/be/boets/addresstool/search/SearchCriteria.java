package be.boets.addresstool.search;

public record SearchCriteria(String firstName, String name,
                             String street, Integer number,
                             String postalCode, String city) {
}
