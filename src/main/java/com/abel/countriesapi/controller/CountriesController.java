package com.abel.countriesapi.controller;

import com.abel.countriesapi.dto.request.CityRequest;
import com.abel.countriesapi.dto.response.CityData;
import com.abel.countriesapi.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class CountriesController {

   // GET the top <number_of_cities> cities in order of population (descending) of Italy, New Zealand and Ghana, where <number_of_cities> is a query parameter passed in the query. If there are not enough cities, just return the ones you can find;

    //An endpoint that takes a country as a parameter (e.g. Italy, Nigeria, …) and returns:
    //population
    //capital city
    //location
    //currency
    //ISO2&3

    //An endpoint that takes a country as a parameter (e.g. Italy, Nigeria, …) and returns the full list of all the states in the country and all the cities in each state.

    //An endpoint that takes a country as a parameter (e.g. Italy, Nigeria, …), a monetary amount and a target currency and provides:
    //the country currency
    //and (using the csv file provided) converts the amount to the target currency and formats it correctly.

    private final CityService cityService;

    @GetMapping("/cities/top")
    public ResponseEntity<List<CityData>> getTopCities(
            @RequestParam("number_of_cities") int numberOfCities
    ) {
        List<String> countries = List.of("Italy", "New Zealand", "Ghana");
        List<CityData> topCities = cityService.getTopCitiesByPopulation(countries, numberOfCities);

        return new ResponseEntity<>(topCities, HttpStatus.OK);
    }


    //clarify the endpoint below
    @PostMapping("/cities/population")
    public ResponseEntity<String> getCityPopulation(@RequestBody CityRequest cityRequest) {

        String city = cityRequest.getCity();

        // Your logic to process the city and retrieve the population

        // For example, you can return a static response for now
        String response = "The population of " + city + " is 10 million";

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
