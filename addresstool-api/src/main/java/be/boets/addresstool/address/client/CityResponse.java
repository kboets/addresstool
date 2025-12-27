package be.boets.addresstool.address.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CityResponse(@JsonProperty("Postcode") PostcodeData postcode
) {
    public record PostcodeData(
            @JsonProperty("postcode_deelgemeente") String postalCode,
            @JsonProperty("naam_deelgemeente") String name,
            @JsonProperty("naam_hoofdgemeente") String mainName
    ) {
    }
}
