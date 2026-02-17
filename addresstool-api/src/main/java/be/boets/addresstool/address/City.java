package be.boets.addresstool.address;

import jakarta.persistence.Column;

import java.io.Serializable;
import java.util.Objects;

public class City implements Serializable {
    @Column(name = "city_name", nullable = false)
    private String name;

    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    protected City() {
    }

    public City(String name, String postalCode) {
        this.name = name;
        this.postalCode = postalCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Objects.equals(name, city.name) && Objects.equals(postalCode, city.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, postalCode);
    }

    public static final class CityBuilder {
        private String name;
        private String postalCode;

        private CityBuilder() {
        }

        public static CityBuilder aCity() {
            return new CityBuilder();
        }

        public CityBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public CityBuilder withPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public City build() {
            City city = new City();
            city.setName(name);
            city.setPostalCode(postalCode);
            return city;
        }
    }
}
