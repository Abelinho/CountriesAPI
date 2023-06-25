package com.abel.countriesapi.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CountryStateData implements Serializable {

    private String name;
    private String iso3;
    private String iso2;
    private List<StateData> states;

}
