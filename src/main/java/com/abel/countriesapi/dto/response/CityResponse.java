package com.abel.countriesapi.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class CityResponse {

    private List<CityData> data;

}
