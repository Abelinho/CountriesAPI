package com.abel.countriesapi.dto.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.io.Serializable;

@Data
public class StateData implements Serializable {

    private String name;
    @JsonAlias("state_code")
    private String stateCode;

}
