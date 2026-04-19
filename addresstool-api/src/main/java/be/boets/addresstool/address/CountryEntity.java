package be.boets.addresstool.address;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(
        name = "country",
        schema = "addresstool"
)
public class CountryEntity implements Serializable {
    @Id
    @SequenceGenerator(
            name = "country_id_seq",
            sequenceName = "country_id_seq",
            schema = "addresstool",
            allocationSize =  1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "country_id_seq")
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String countryCode;
    @Column(nullable = false)
    private String phoneCode;
    @Column
    private String flagUrl;

    protected CountryEntity() {
    }

    public CountryEntity(String name, String countryCode, String phoneCode, String flagUrl) {
        this.name = name;
        this.countryCode = countryCode;
        this.phoneCode = phoneCode;
        this.flagUrl = flagUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public String getFlagUrl() {
        return flagUrl;
    }

    public void setFlagUrl(String flagUrl) {
        this.flagUrl = flagUrl;
    }


    public static final class CountryEntityBuilder {
        private Integer id;
        private String name;
        private String countryCode;
        private String phoneCode;
        private String flagUrl;

        private CountryEntityBuilder() {
        }

        public static CountryEntityBuilder aCountryEntity() {
            return new CountryEntityBuilder();
        }

        public CountryEntityBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public CountryEntityBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public CountryEntityBuilder withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public CountryEntityBuilder withPhoneCode(String phoneCode) {
            this.phoneCode = phoneCode;
            return this;
        }

        public CountryEntityBuilder withFlagUrl(String flagUrl) {
            this.flagUrl = flagUrl;
            return this;
        }

        public CountryEntity build() {
            CountryEntity countryEntity = new CountryEntity();
            countryEntity.setId(id);
            countryEntity.setName(name);
            countryEntity.setCountryCode(countryCode);
            countryEntity.setPhoneCode(phoneCode);
            countryEntity.setFlagUrl(flagUrl);
            return countryEntity;
        }
    }
}
