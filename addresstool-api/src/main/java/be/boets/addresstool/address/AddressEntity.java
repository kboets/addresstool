package be.boets.addresstool.address;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;

import java.io.Serializable;
import java.util.Objects;

public class AddressEntity implements Serializable {
    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "number", nullable = false)
    private String number;

    @Column(name = "box")
    private String box;

    @Embedded
    private CityEntity cityEntity;

    public AddressEntity() {
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBox() {
        return box;
    }

    public void setBox(String box) {
        this.box = box;
    }

    public CityEntity getCity() {
        return cityEntity;
    }

    public void setCity(CityEntity cityEntity) {
        this.cityEntity = cityEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AddressEntity addressEntity = (AddressEntity) o;
        return Objects.equals(number, addressEntity.number) && Objects.equals(street, addressEntity.street) && Objects.equals(box, addressEntity.box) && Objects.equals(cityEntity, addressEntity.cityEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, number, box, cityEntity);
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", number=" + number +
                ", box='" + box + '\'' +
                ", city=" + cityEntity +
                '}';
    }


    public static final class AddressEntityBuilder {
        private String street;
        private String number;
        private String box;
        private CityEntity cityEntity;

        private AddressEntityBuilder() {
        }

        public static AddressEntityBuilder anAddressEntity() {
            return new AddressEntityBuilder();
        }

        public AddressEntityBuilder withStreet(String street) {
            this.street = street;
            return this;
        }

        public AddressEntityBuilder withNumber(String number) {
            this.number = number;
            return this;
        }

        public AddressEntityBuilder withBox(String box) {
            this.box = box;
            return this;
        }

        public AddressEntityBuilder withCityEntity(CityEntity cityEntity) {
            this.cityEntity = cityEntity;
            return this;
        }

        public AddressEntity build() {
            AddressEntity addressEntity = new AddressEntity();
            addressEntity.setStreet(street);
            addressEntity.setNumber(number);
            addressEntity.setBox(box);
            addressEntity.cityEntity = this.cityEntity;
            return addressEntity;
        }
    }
}
