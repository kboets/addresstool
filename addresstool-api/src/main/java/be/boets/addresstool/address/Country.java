package be.boets.addresstool.address;

public record Country(String name, String countryCode, String flagUrl, String phoneCode) {

    public static final class CountryBuilder {
        private String name;
        private String countryCode;
        private String flagUrl;
        private String phoneCode;

        private CountryBuilder() {
        }

        public static CountryBuilder aCountry() {
            return new CountryBuilder();
        }

        public CountryBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public CountryBuilder withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public CountryBuilder withFlagUrl(String flagUrl) {
            this.flagUrl = flagUrl;
            return this;
        }

        public CountryBuilder withPhoneCode(String phoneCode) {
            this.phoneCode = phoneCode;
            return this;
        }

        public Country build() {
            return new Country(name, countryCode, flagUrl, phoneCode);
        }
    }
}
