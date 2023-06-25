package com.abel.countriesapi.service;

import com.abel.countriesapi.dto.request.CountryCityPopulationRequest;
import com.abel.countriesapi.dto.response.CapitalData;
import com.abel.countriesapi.dto.response.CapitalResponse;
import com.abel.countriesapi.dto.response.CityData;
import com.abel.countriesapi.dto.response.AppResponse;
import com.abel.countriesapi.dto.response.CityResponse;
import com.abel.countriesapi.dto.response.CountryCitiesResponse;
import com.abel.countriesapi.dto.response.CountryInformation;
//import com.abel.countriesapi.dto.response.CountryPopulationResponse;
import com.abel.countriesapi.dto.response.CountryStatesCities;
import com.abel.countriesapi.dto.response.CountryStatesResponse;
import com.abel.countriesapi.dto.response.CurrencyData;
import com.abel.countriesapi.dto.response.CurrencyResponse;
import com.abel.countriesapi.dto.response.IsoData;
import com.abel.countriesapi.dto.response.IsoResponse;
import com.abel.countriesapi.dto.response.LocationData;
import com.abel.countriesapi.dto.response.LocationResponse;
//import com.abel.countriesapi.dto.response.PopulationCount;
import com.abel.countriesapi.dto.response.PopulationResponse;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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

//    public List<CityData> getTopCitiesByPopulation(List<String> countries, CountryCityPopulationRequest request,Integer numOfCities) {
//        List<CityData> allCities = new ArrayList<>();
//        Map<String, CountryCityPopulationRequest> params = new HashMap<>();
//        //params.put("payload", request);
//
//        for (String country : countries) {
//           // String url = apiBaseUrl + "/countries/population/cities?country=" + country;
//           // CityResponse cityResponse = restTemplate.getForObject(url, CityResponse.class);
//            String url = apiBaseUrl + "/population/cities/filter";
//            CityResponse cityResponse = restTemplate.postForObject(url,request,CityResponse.class);
//
//            if (cityResponse != null && cityResponse.getData() != null) {
//                allCities.addAll(cityResponse.getData());
//            }
//        }
//
//        allCities.sort(Comparator.comparingInt(CityData::getPopulation).reversed());
//
//        if (allCities.size() <= numOfCities) {
//            return allCities;
//        } else {
//            return allCities.subList(0, numOfCities);
//        }
//    }

    public CityResponse getTopCitiesByPopulation(String country, Integer numOfCities) throws URISyntaxException {
    //    List<CityData> allCities = new ArrayList<>();//was returned originally
        List<CityResponse> allCities = new ArrayList<>();

        String url = apiBaseUrl + "/countries/population/cities/filter/q?limit={limit}&order={order}&orderBy={orderBy}&country={country}";
       // System.out.println(url);
//        URI uri = new URI(url);


            HttpHeaders headers = new HttpHeaders();
            //headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<?> entity = new HttpEntity<>(headers);

            Map<String, String> params = new HashMap<>();
            params.put("limit", ""+numOfCities);
            params.put("order","dsc");
            params.put("orderBy","population");
            params.put("country",country);

            log.info("URL => " + url);
            ResponseEntity<CityResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, CityResponse.class,params);

//            log.info("CityResponse :: {}", response.getBody());

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

    @Async
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

      //  String url = apiBaseUrl + "/countries/" + country + "/population";
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
