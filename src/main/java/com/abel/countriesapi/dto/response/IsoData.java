package com.abel.countriesapi.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class IsoData implements Serializable {

    private String name;
    private String iso2;
    private String iso3;

}
