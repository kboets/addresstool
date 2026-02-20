package be.boets.addresstool.address;

import jakarta.persistence.Column;

import java.io.Serializable;
import java.util.Objects;

public class CityEntity implements Serializable {
    @Column(name = "city_name", nullable = false)
    private String name;

    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    protected CityEntity() {
    }

    public CityEntity(String name, String postalCode) {
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
        CityEntity cityEntity = (CityEntity) o;
        return Objects.equals(name, cityEntity.name) && Objects.equals(postalCode, cityEntity.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, postalCode);
    }


    public static final class CityEntityBuilder {
        private String name;
        private String postalCode;

        private CityEntityBuilder() {
        }

        public static CityEntityBuilder aCityEntity() {
            return new CityEntityBuilder();
        }

        public CityEntityBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public CityEntityBuilder withPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public CityEntity build() {
            CityEntity cityEntity = new CityEntity();
            cityEntity.setName(name);
            cityEntity.setPostalCode(postalCode);
            return cityEntity;
        }
    }
}
