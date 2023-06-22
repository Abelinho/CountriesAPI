package com.abel.countriesapi.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CountryCitiesResponse implements Serializable {

    private List<String> cities;

}
