package be.boets.addresstool.address;

public record Address(String street, int number, String box, City city) {


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
            return new Address(street, number, box, city);
        }
    }
}
