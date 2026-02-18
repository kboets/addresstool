package be.boets.addresstool.address;

public record AddressRecord(String street, int number, String box, CityRecord cityRecord) {


    public static final class AddressRecordBuilder {
        private String street;
        private int number;
        private String box;
        private CityRecord cityRecord;

        private AddressRecordBuilder() {
        }

        public static AddressRecordBuilder anAddressRecord() {
            return new AddressRecordBuilder();
        }

        public AddressRecordBuilder withStreet(String street) {
            this.street = street;
            return this;
        }

        public AddressRecordBuilder withNumber(int number) {
            this.number = number;
            return this;
        }

        public AddressRecordBuilder withBox(String box) {
            this.box = box;
            return this;
        }

        public AddressRecordBuilder withCityRecord(CityRecord cityRecord) {
            this.cityRecord = cityRecord;
            return this;
        }

        public AddressRecord build() {
            return new AddressRecord(street, number, box, cityRecord);
        }
    }
}
