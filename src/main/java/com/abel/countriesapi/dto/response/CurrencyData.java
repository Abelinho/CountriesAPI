package com.abel.countriesapi.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class CurrencyData implements Serializable {

    private String name;
    private String currency;
    private String iso2;
    private String iso3;

}
