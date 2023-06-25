package com.abel.countriesapi.dto.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class CityResponse {
    private boolean error;
    @JsonAlias("msg")
    private String message;
    private List<CityData> data;
}
