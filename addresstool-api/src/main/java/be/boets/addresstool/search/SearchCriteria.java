package be.boets.addresstool.search;

public record SearchCriteria(String firstName, String name,
                             String street, String number,
                             String postalCode, String city) {


    public static final class SearchCriteriaBuilder {
        private String firstName;
        private String name;
        private String street;
        private String number;
        private String postalCode;
        private String city;

        private SearchCriteriaBuilder() {
        }

        public static SearchCriteriaBuilder aSearchCriteria() {
            return new SearchCriteriaBuilder();
        }

        public SearchCriteriaBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public SearchCriteriaBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public SearchCriteriaBuilder withStreet(String street) {
            this.street = street;
            return this;
        }

        public SearchCriteriaBuilder withNumber(String number) {
            this.number = number;
            return this;
        }

        public SearchCriteriaBuilder withPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public SearchCriteriaBuilder withCity(String city) {
            this.city = city;
            return this;
        }

        public SearchCriteria build() {
            return new SearchCriteria(firstName, name, street, number, postalCode, city);
        }
    }
}
