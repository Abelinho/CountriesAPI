package com.abel.countriesapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyData implements Serializable {

    private String name;
    private String currency;
    private String iso2;
    private String iso3;

}
