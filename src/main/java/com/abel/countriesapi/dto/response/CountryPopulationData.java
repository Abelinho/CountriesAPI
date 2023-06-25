package com.abel.countriesapi.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CountryPopulationData implements Serializable {

    private String country;
    private String code;
    private String iso3;
    private List<PopulationCounts> populationCounts;
}
