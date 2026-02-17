package be.boets.addresstool.address;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;

import java.io.Serializable;
import java.util.Objects;

public class Address implements Serializable {
    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "number", nullable = false)
    private int number;

    @Column(name = "box")
    private String box;

    @Embedded
    private City city;

    public Address() {
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getBox() {
        return box;
    }

    public void setBox(String box) {
        this.box = box;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return number == address.number && Objects.equals(street, address.street) && Objects.equals(box, address.box) && Objects.equals(city, address.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, number, box, city);
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", number=" + number +
                ", box='" + box + '\'' +
                ", city=" + city +
                '}';
    }

    public static final class AddressBuilder {
        private String street;
        private int number;
        private String box;
        private City city;

        private AddressBuilder() {
        }

        public static AddressBuilder anAddress() {
            return new AddressBuilder();
        }

        public AddressBuilder withStreet(String street) {
            this.street = street;
            return this;
        }

        public AddressBuilder withNumber(int number) {
            this.number = number;
            return this;
        }

        public AddressBuilder withBox(String box) {
            this.box = box;
            return this;
        }

        public AddressBuilder withCity(City city) {
            this.city = city;
            return this;
        }

        public Address build() {
            Address address = new Address();
            address.setStreet(street);
            address.setNumber(number);
            address.setBox(box);
            address.setCity(city);
            return address;
        }
    }
}
