package com.abel.countriesapi.controller;

import com.abel.countriesapi.dto.request.CityRequest;
import com.abel.countriesapi.dto.request.CountryCityPopulationRequest;
import com.abel.countriesapi.dto.response.CityData;
import com.abel.countriesapi.dto.response.CityResponse;
import com.abel.countriesapi.dto.response.CountryInformation;
import com.abel.countriesapi.dto.response.CountryStatesCities;
import com.abel.countriesapi.service.CityService;
import com.abel.countriesapi.service.CurrencyConversionService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
@Slf4j
@Api(tags = "Countries-api")
public class CountriesController {

    private final CityService cityService;
    private final CurrencyConversionService currencyConversionService;

    @GetMapping("/cities/top")
    public ResponseEntity<CityResponse> getTopCities(@RequestParam Integer numberOfCity, @RequestParam String country) {
        //List<String> countries = List.of("Italy", "New Zealand", "Ghana");
        CityResponse topCities;

        topCities = cityService.getTopCitiesByPopulation(country, numberOfCity);

        return new ResponseEntity<>(topCities, HttpStatus.OK);
    }

    @GetMapping("/countries/information")
    public ResponseEntity<CountryInformation> getCountryInformation(@RequestParam String country) {
        CountryInformation countryInformation = cityService.getCountryInformation(country);

        if (countryInformation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(countryInformation, HttpStatus.OK);
    }

    @GetMapping("/countries/states/cities")
    public ResponseEntity<CountryStatesCities> getCountryStatesAndCities(@RequestParam String country) {
        CountryStatesCities countryStatesCities = cityService.getCountryStatesAndCities(country);

        if (countryStatesCities == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(countryStatesCities, HttpStatus.OK);
    }

    @GetMapping("/countries/convert-currency")
    public ResponseEntity<String> convertCurrency(
            @RequestParam("country") String country,
            @RequestParam("amount") BigDecimal amount,
            @RequestParam("targetCurrency") String targetCurrency) {

        log.info("country: "+country+"amount: "+amount+" currency: "+targetCurrency);
        String countryCurrency = cityService.getCountryCurrency(country);
        log.info("Base currency: "+countryCurrency);
        BigDecimal convertedAmount = currencyConversionService.convertCurrency(countryCurrency, targetCurrency, amount);

        String response = String.format("%s %.2f = %s %.2f", countryCurrency, amount, targetCurrency, convertedAmount);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
