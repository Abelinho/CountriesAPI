package com.abel.countriesapi.dto.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CountryInformation {

    private String country;
    private List population;
    private String capital;
    private List<Integer> location;
    private String currency;
    private String iso;
}
