package com.abel.countriesapi.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class CurrencyResponse implements Serializable {

    private boolean error;
    private String msg;
    private CurrencyData data;
}
