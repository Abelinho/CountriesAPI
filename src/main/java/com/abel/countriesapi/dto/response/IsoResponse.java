package com.abel.countriesapi.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class IsoResponse implements Serializable {

    private boolean error;
    private String msg;
    private IsoData data;
}
