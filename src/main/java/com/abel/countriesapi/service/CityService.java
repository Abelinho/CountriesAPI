package com.abel.countriesapi.service;

import com.abel.countriesapi.dto.response.CityData;
import com.abel.countriesapi.dto.response.CityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class CityService {

    private final RestTemplate restTemplate;
    private final String apiBaseUrl;

    @Autowired
    public CityService(RestTemplate restTemplate, @Value("${api.base.url}") String apiBaseUrl) {
        this.restTemplate = restTemplate;
        this.apiBaseUrl = apiBaseUrl;
    }

    public List<CityData> getTopCitiesByPopulation(List<String> countries, int numberOfCities) {

        List<CityData> allCities = new ArrayList<>();

        for (String country : countries) {
            String url = apiBaseUrl + "/countries/population/cities?country=" + country;
            CityResponse cityResponse = restTemplate.getForObject(url, CityResponse.class);

            if (cityResponse != null && cityResponse.getData() != null) {
                allCities.addAll(cityResponse.getData());
            }
        }

        allCities.sort(Comparator.comparingInt(CityData::getPopulation).reversed());

        if (allCities.size() <= numberOfCities) {
            return allCities;
        } else {
            return allCities.subList(0, numberOfCities);
        }

    }
}
