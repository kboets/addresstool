package be.boets.addresstool.address.client;

import java.util.List;
import java.util.Map;

public record CountryResponse(Flags flags,
                              Name name,
                              String cca2,
                              Idd idd) {
    public record Flags(
            String png,
            String svg,
            String alt
    ) {}

    public record Name(
            String common,
            String official,
            Map<String, NativeName> nativeName
    ) {}

    public record NativeName(
            String official,
            String common
    ) {}

    public record Idd(
            String root,
            List<String> suffixes
    ) {}


    public static final class CountryResponseBuilder {
        private Flags flags;
        private Name name;
        private String cca2;
        private Idd idd;

        private CountryResponseBuilder() {
        }

        public static CountryResponseBuilder aCountryResponse() {
            return new CountryResponseBuilder();
        }

        public CountryResponseBuilder withFlags(Flags flags) {
            this.flags = flags;
            return this;
        }

        public CountryResponseBuilder withName(Name name) {
            this.name = name;
            return this;
        }

        public CountryResponseBuilder withCca2(String cca2) {
            this.cca2 = cca2;
            return this;
        }

        public CountryResponseBuilder withIdd(Idd idd) {
            this.idd = idd;
            return this;
        }

        public CountryResponse build() {
            return new CountryResponse(flags, name, cca2, idd);
        }
    }
}
