package com.abel.countriesapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryInformation {

    private String country;
    private Object population;
    private String capital;
    private List<Integer> location;
    private String currency;
    private String iso;
}
