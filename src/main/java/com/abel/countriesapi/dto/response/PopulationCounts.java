package com.abel.countriesapi.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class PopulationCounts implements Serializable {

    private String year;

    private String value;
}
