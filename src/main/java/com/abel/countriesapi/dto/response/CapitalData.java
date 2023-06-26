package com.abel.countriesapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CapitalData implements Serializable {

    private String name;
    private String capital;
    private String iso2;
    private String iso3;
}
