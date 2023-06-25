package com.abel.countriesapi.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class CityData {

    private String city;
    private String country;
    private List<PopulationCountsFilter> populationCounts;
}
