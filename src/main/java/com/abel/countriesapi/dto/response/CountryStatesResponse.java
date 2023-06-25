package com.abel.countriesapi.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CountryStatesResponse implements Serializable {

    private boolean error;
    private String msg;
    private CountryStateData data;

    //private List<String> states;
}
