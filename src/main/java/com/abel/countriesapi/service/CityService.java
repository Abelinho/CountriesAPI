package com.abel.countriesapi.service;

import com.abel.countriesapi.dto.response.CapitalData;
import com.abel.countriesapi.dto.response.CapitalResponse;
import com.abel.countriesapi.dto.response.CityData;
import com.abel.countriesapi.dto.response.AppResponse;
import com.abel.countriesapi.dto.response.CityResponse;
import com.abel.countriesapi.dto.response.CountryCitiesResponse;
import com.abel.countriesapi.dto.response.CountryInformation;
import com.abel.countriesapi.dto.response.CountryPopulationResponse;
import com.abel.countriesapi.dto.response.CountryStatesCities;
import com.abel.countriesapi.dto.response.CountryStatesResponse;
import com.abel.countriesapi.dto.response.CurrencyData;
import com.abel.countriesapi.dto.response.IsoData;
import com.abel.countriesapi.dto.response.LocationData;
import com.abel.countriesapi.dto.response.LocationResponse;
import com.abel.countriesapi.dto.response.PopulationCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CityService {

    private final RestTemplate restTemplate;

    @Value("${countriesapi.baseurl}")
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

    //make this asynchronous
    public CountryInformation getCountryInformation(String country) {

        CountryInformation countryInformation = new CountryInformation();

        countryInformation.setCountry(country);
        countryInformation.setPopulation(getCountryPopulation(country));
        countryInformation.setCapital(getCountryCapital(country));
        countryInformation.setLocation(getCountryLocation(country));
        countryInformation.setCurrency(getCountryCurrency(country));
        countryInformation.setIso(getCountryISO(country));

        return countryInformation;
    }

    private String getCountryISO(String country) {

        String url = apiBaseUrl + "/countries/" + country + "/iso";
        AppResponse response = restTemplate.getForObject(url, AppResponse.class);

        IsoData isoData = (IsoData) response.getData();

        return isoData.getIso2() + ":"+isoData.getIso3();
    }

    public String getCountryCurrency(String country) {

        String url = apiBaseUrl + "/countries/" + country + "/currency";

        AppResponse response = restTemplate.getForObject(url, AppResponse.class);

       CurrencyData currencyData = (CurrencyData) response.getData();

        return currencyData.getCurrency();
    }

    private List<Integer> getCountryLocation(String country) {

        ArrayList<Integer> positions = new ArrayList<>();

        String url = apiBaseUrl + "/countries/" + country + "/positions";

     //   LocationResponse capitalResponse = restTemplate.getForObject(url, LocationResponse.class);
        AppResponse capitalResponse = restTemplate.getForObject(url, AppResponse.class);

       LocationData locationData = (LocationData)capitalResponse.getData();

        positions.add(locationData.getLat());
        positions.add(locationData.getLongitude());

        return positions;

    }

    private String getCountryCapital(String country) {

        String url = apiBaseUrl + "/countries/" + country + "/capital";

        CapitalResponse capitalResponse = restTemplate.getForObject(url, CapitalResponse.class);

       return capitalResponse.getData().getCapital();



    }

    private List getCountryPopulation(String country) {

        String url = apiBaseUrl + "/countries/" + country + "/population";
        CountryPopulationResponse populationResponse = restTemplate.getForObject(url, CountryPopulationResponse.class);

        if (populationResponse != null && populationResponse.getData() != null) {
            return populationResponse.getData();
        }

        return null;
    }

    public CountryStatesCities getCountryStatesAndCities(String country) {

        String statesUrl = apiBaseUrl + "/countries/" + country + "/states";

        CountryStatesResponse statesResponse = restTemplate.getForObject(statesUrl, CountryStatesResponse.class);

        if (statesResponse != null) {
            CountryStatesCities countryStatesCities = new CountryStatesCities();
            countryStatesCities.setCountry(country);
            countryStatesCities.setStates(statesResponse.getStates());

            Map<String, List<String>> citiesMap = new HashMap<>();
            for (String state : statesResponse.getStates()) {
                String citiesUrl = apiBaseUrl + "/countries/" + country + "/" + state + "/cities";
                CountryCitiesResponse citiesResponse = restTemplate.getForObject(citiesUrl, CountryCitiesResponse.class);
                if (citiesResponse != null) {
                    citiesMap.put(state, citiesResponse.getCities());
                }
            }

            countryStatesCities.setCities(citiesMap);
            return countryStatesCities;
        }

        return null;

    }
}
