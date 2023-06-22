package com.abel.countriesapi.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class CountryStatesCities implements Serializable {

    private String country;
    private List<String> states;
    private Map<String, List<String>> cities;
}
