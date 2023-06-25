package com.abel.countriesapi.dto.response;

import lombok.Data;

@Data
public class PopulationCountsFilter {

    private String year;
    private String value;
    private String sex;
    private String reliabilty;
}
