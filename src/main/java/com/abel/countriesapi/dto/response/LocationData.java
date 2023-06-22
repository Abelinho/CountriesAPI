package com.abel.countriesapi.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LocationData {

    private String name;
    private String iso2;

    @JsonProperty("long")
    private Integer longitude;
    private Integer lat;
}
