package be.boets.addresstool.address;

public record City(String name, String postalCode, boolean isMain) {


    public static final class CityBuilder {
        private String name;
        private String postalCode;
        private boolean isMain;

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

        public CityBuilder withIsMain(boolean isMain) {
            this.isMain = isMain;
            return this;
        }

        public City build() {
            return new City(name, postalCode, isMain);
        }
    }
}
