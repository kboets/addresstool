package be.boets.addresstool.address;

public record CityRecord(String name, String postalCode, boolean isMain) {

    public static final class CityRecordBuilder {
        private String name;
        private String postalCode;
        private boolean isMain;

        private CityRecordBuilder() {
        }

        public static CityRecordBuilder aCityRecord() {
            return new CityRecordBuilder();
        }

        public CityRecordBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public CityRecordBuilder withPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public CityRecordBuilder withIsMain(boolean isMain) {
            this.isMain = isMain;
            return this;
        }

        public CityRecord build() {
            return new CityRecord(name, postalCode, isMain);
        }
    }
}
