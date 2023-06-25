package com.abel.countriesapi.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class PopulationResponse implements Serializable {

    private boolean error;
    private String msg;
    private CountryPopulationData data;
}
