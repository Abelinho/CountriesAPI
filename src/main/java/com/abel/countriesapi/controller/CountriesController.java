package com.abel.countriesapi.controller;

import com.abel.countriesapi.dto.request.CityRequest;
import com.abel.countriesapi.dto.response.CityData;
import com.abel.countriesapi.dto.response.CountryInformation;
import com.abel.countriesapi.dto.response.CountryStatesCities;
import com.abel.countriesapi.service.CityService;
import com.abel.countriesapi.service.CurrencyConversionService;
import lombok.RequiredArgsConstructor;
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

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class CountriesController {

    private final CityService cityService;
    private final CurrencyConversionService currencyConversionService;

    @GetMapping("/cities/top")
    public ResponseEntity<List<CityData>> getTopCities(
            @RequestParam("number_of_cities") int numberOfCities
    ) {
        List<String> countries = List.of("Italy", "New Zealand", "Ghana");
        List<CityData> topCities = cityService.getTopCitiesByPopulation(countries, numberOfCities);

        return new ResponseEntity<>(topCities, HttpStatus.OK);
    }

    @GetMapping("/countries/{country}/information")
    public ResponseEntity<CountryInformation> getCountryInformation(@PathVariable("country") String country) {
        CountryInformation countryInformation = cityService.getCountryInformation(country);

        if (countryInformation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(countryInformation, HttpStatus.OK);
    }

    @GetMapping("/countries/{country}/states-cities")
    public ResponseEntity<CountryStatesCities> getCountryStatesAndCities(@PathVariable("country") String country) {
        CountryStatesCities countryStatesCities = cityService.getCountryStatesAndCities(country);

        if (countryStatesCities == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(countryStatesCities, HttpStatus.OK);
    }

    @GetMapping("/countries/{country}/convert-currency")
    public ResponseEntity<String> convertCurrency(
            @PathVariable("country") String country,
            @RequestParam("amount") BigDecimal amount,
            @RequestParam("targetCurrency") String targetCurrency) {

        String countryCurrency = cityService.getCountryCurrency(country);
        BigDecimal convertedAmount = currencyConversionService.convertCurrency(countryCurrency, targetCurrency, amount);

        String response = String.format("%s %.2f = %s %.2f", countryCurrency, amount, targetCurrency, convertedAmount);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //clarify the endpoint below
//    @PostMapping("/cities/population")
//    public ResponseEntity<String> getCityPopulation(@RequestBody CityRequest cityRequest) {
//
//        String city = cityRequest.getCity();
//
//        // Your logic to process the city and retrieve the population
//
//        // For example, you can return a static response for now
//        String response = "The population of " + city + " is 10 million";
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

}
