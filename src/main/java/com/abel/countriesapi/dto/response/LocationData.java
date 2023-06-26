package com.abel.countriesapi.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationData implements Serializable {

    private String name;
    private String iso2;

    @JsonProperty("long")
    private Integer longitude;
    private Integer lat;
}
