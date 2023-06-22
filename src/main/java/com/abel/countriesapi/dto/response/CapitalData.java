package com.abel.countriesapi.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class CapitalData implements Serializable {

    private String name;
    private String capital;
    private String iso2;
    private String iso3;
}
