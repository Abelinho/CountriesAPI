package com.abel.countriesapi.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CityRequest {

    @NotEmpty(message = "packageName cannot be empty")
    private String city;
}
