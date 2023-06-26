package com.abel.countriesapi.service;

import com.abel.countriesapi.dto.response.CapitalResponse;
import com.abel.countriesapi.dto.response.CityResponse;
import com.abel.countriesapi.dto.response.CountryInformation;
import com.abel.countriesapi.dto.response.CountryStatesCities;
import com.abel.countriesapi.dto.response.CountryStatesResponse;
import com.abel.countriesapi.dto.response.CurrencyResponse;
import com.abel.countriesapi.dto.response.IsoData;
import com.abel.countriesapi.dto.response.IsoResponse;
import com.abel.countriesapi.dto.response.LocationData;
import com.abel.countriesapi.dto.response.LocationResponse;
import com.abel.countriesapi.dto.response.PopulationResponse;
import com.abel.countriesapi.dto.response.StateCitiesResponse;
import com.abel.countriesapi.dto.response.StateData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CityService {

    private final RestTemplate restTemplate;
    private final String apiBaseUrl;

    @Autowired
    public CityService(RestTemplate restTemplate, @Value("${countriesapi.baseurl}") String apiBaseUrl) {
        this.restTemplate = restTemplate;
        this.apiBaseUrl = apiBaseUrl;
    }

    public CityResponse getTopCitiesByPopulation(String country, Integer numOfCities) {

        List<CityResponse> allCities = new ArrayList<>();

        String url = apiBaseUrl + "/countries/population/cities/filter/q?limit={limit}&order={order}&orderBy={orderBy}&country={country}";

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<?> entity = new HttpEntity<>(headers);

            Map<String, String> params = new HashMap<>();
            params.put("limit", ""+numOfCities);
            params.put("order","dsc");
            params.put("orderBy","population");
            params.put("country",country);

            log.info("URL => " + url);
            ResponseEntity<CityResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, CityResponse.class,params);

        return response.getBody();
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

    @Async
    public String getCountryISO(String country) {

        String url = apiBaseUrl + "/countries/iso/q?&country={country}";

        HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<?> entity = new HttpEntity<>(headers);

        Map<String, String> params = new HashMap<>();
        params.put("country",country);

        log.info("URL => " + url);
        ResponseEntity<IsoResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, IsoResponse.class,params);

        IsoData isoData = response.getBody().getData();

        return isoData.getIso2() + ":"+isoData.getIso3();
    }

    //@Async
    public String getCountryCurrency(String country) {

        String url = apiBaseUrl + "/countries/currency/q?&country={country}";

        HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<?> entity = new HttpEntity<>(headers);

        Map<String, String> params = new HashMap<>();
        params.put("country",country);

        log.info("URL => " + url);
        ResponseEntity<CurrencyResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, CurrencyResponse.class,params);

        return response.getBody().getData().getCurrency();
    }

    @Async
    public List<Integer> getCountryLocation(String country) {

        ArrayList<Integer> positions = new ArrayList<>();

        String url = apiBaseUrl + "/countries/positions/q?&country={country}";

        HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<?> entity = new HttpEntity<>(headers);

        Map<String, String> params = new HashMap<>();
        params.put("country",country);

        log.info("URL => " + url);
        ResponseEntity<LocationResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, LocationResponse.class,params);

        LocationData locationData = response.getBody().getData();

        positions.add(locationData.getLat());
        positions.add(locationData.getLongitude());

        return positions;

    }

    @Async
    public String getCountryCapital(String country) {

       // String url = apiBaseUrl + "/countries/" + country + "/capital";
        String url = apiBaseUrl + "/countries/capital/q?&country={country}";

        HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<?> entity = new HttpEntity<>(headers);

        Map<String, String> params = new HashMap<>();
        params.put("country",country);

        log.info("URL => " + url);
        ResponseEntity<CapitalResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, CapitalResponse.class,params);

       return response.getBody().getData().getCapital();
    }

    @Async
    public PopulationResponse getCountryPopulation(String country) {

          String url = apiBaseUrl + "/countries/population/q?&country={country}";

        HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<?> entity = new HttpEntity<>(headers);

        Map<String, String> params = new HashMap<>();
        params.put("country",country);

        log.info("URL => " + url);
        ResponseEntity<PopulationResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, PopulationResponse.class,params);


        if (response != null && response.getBody() != null) {
            return response.getBody();
        }

        return null;
    }

    public CountryStatesCities getCountryStatesAndCities(String country) {

        String url = apiBaseUrl + "/countries/states/q?&country={country}";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<?> entity = new HttpEntity<>(headers);

        Map<String, String> params = new HashMap<>();
        params.put("country",country);

        log.info("URL => " + url);
        ResponseEntity<CountryStatesResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, CountryStatesResponse.class,params);

        CountryStatesResponse countryStatesResponse = response.getBody();

        if (response != null&& response.getBody() != null) {
            CountryStatesCities countryStatesCities = new CountryStatesCities();
            countryStatesCities.setCountry(country);
            countryStatesCities.setStates(countryStatesResponse.getData().getStates());
            countryStatesCities.setCities(getCitiesInState(countryStatesResponse,country));
            return countryStatesCities;
        }

        return null;

    }

    @Async
    public Map<String, List<String>> getCitiesInState(CountryStatesResponse countryStatesResponse, String country){

        Map<String, List<String>> citiesMap = new HashMap<>();
        for (StateData state : countryStatesResponse.getData().getStates()) {

            if (state.getName().equals("Lagos State")){//for Lagos special case!!
                state.setName("Lagos");
            }


             log.info("State: "+state.getName());
            String citiesUrl = apiBaseUrl + "/countries/state/cities/q?&country={country}&state={state}";

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<?> entity = new HttpEntity<>(headers);//entity to be written to the request

            Map<String, String> params = new HashMap<>();
            params.put("country",country);
            params.put("state",state.getName());

            log.info("URL => " + citiesUrl);
            ResponseEntity<StateCitiesResponse> response = restTemplate.exchange(citiesUrl, HttpMethod.GET, entity, StateCitiesResponse.class,params);

            log.info("Cities in state: "+response.getBody().getData().toString());

            if (response != null && response.getBody()!=null) {
                citiesMap.put(state.getName(), response.getBody().getData());

            }
        }

        return citiesMap;
    }
}
