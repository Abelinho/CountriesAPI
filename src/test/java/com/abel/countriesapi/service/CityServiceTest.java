package com.abel.countriesapi.service;

import com.abel.countriesapi.dto.response.CapitalData;
import com.abel.countriesapi.dto.response.CapitalResponse;
import com.abel.countriesapi.dto.response.CityData;
import com.abel.countriesapi.dto.response.CityResponse;
import com.abel.countriesapi.dto.response.CountryPopulationData;
import com.abel.countriesapi.dto.response.CurrencyData;
import com.abel.countriesapi.dto.response.CurrencyResponse;
import com.abel.countriesapi.dto.response.IsoData;
import com.abel.countriesapi.dto.response.IsoResponse;
import com.abel.countriesapi.dto.response.LocationData;
import com.abel.countriesapi.dto.response.LocationResponse;
import com.abel.countriesapi.dto.response.PopulationCounts;
import com.abel.countriesapi.dto.response.PopulationCountsFilter;
import com.abel.countriesapi.dto.response.PopulationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

////////


class CityServiceTest {

    @InjectMocks
    private CityService cityService;

    private CityData cityData;

    private IsoResponse isoResponse;

    private CurrencyResponse currencyResponse;

    private LocationResponse locationResponse;

    private CapitalResponse capitalResponse;

    private PopulationResponse populationResponse;

    private CountryPopulationData countryPopulationData;

    @Mock
    private RestTemplate restTemplate;

    @Value("${countriesapi.baseurl}")
    private  String apiBaseUrl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cityService = new CityService(restTemplate,apiBaseUrl);
        PopulationCountsFilter populationCountsFilter = PopulationCountsFilter.builder()
                .reliabilty("reliable")
                .sex("male")
                .value("100")
                .year("2023")
                .build();

        List<PopulationCountsFilter> list = new ArrayList<>();
        list.add(populationCountsFilter);

        cityData = CityData.builder()
                .city("Milan")
                .country("Italy")
                .populationCounts(list)
                .build();

        isoResponse = IsoResponse.builder()
                .error(false)
                .msg("country's ISO code retrieved")
                .data(new IsoData("Nigeria","NG","NGA"))
                .build();

         currencyResponse = CurrencyResponse.builder()
                .error(false)
                .msg("Nigeria and currency retrieved")
                .data(new CurrencyData("Nigeria","NGN","NG","NGA"))
                .build();

        locationResponse = LocationResponse.builder()
                .error(false)
                .msg("country position retrieved")
                .data(new LocationData("Nigeria","NG",8,10))
                .build();

        capitalResponse = CapitalResponse.builder()
                .error(false)
                .msg("country and capitals retrieved")
                .data(new CapitalData("Nigeria","Abuja","NG","NGA"))
                .build();

        countryPopulationData = CountryPopulationData.builder()
                .country("Nigeria")
                .code("NGA")
                .iso3("NGA")
                .populationCounts(List.of(new PopulationCounts("1960","45138458")))
                .build();

        populationResponse = PopulationResponse.builder()
                .error(false)
                .msg("Nigeria with population")
                .data(countryPopulationData)
                .build();
//
//        countryStateData = CountryStateData.builder()
//                .name("Nigeria")
//                .iso3("NGA")
//                .iso2("NG")
//                .states(List.of(new StateData("Abia State","AB")))
//                .build();
//
//        countryStatesResponse = CountryStatesResponse.builder()
//                .error(false)
//                .msg("states in Nigeria retrieved")
//                .data(countryStateData)
//                .build();

    }

    @Test
    void getTopCitiesByPopulation() {

        List<CityData> listOfCity = new ArrayList<>();
        listOfCity.add(cityData);

        // Mock response
        CityResponse mockCityResponse = CityResponse.builder()
                .error(false)
                .message("successful")
                .data(listOfCity)
                .build();
        // Set up the mock response data here

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<CityResponse> mockResponseEntity = new ResponseEntity<>(mockCityResponse, HttpStatus.OK);

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(CityResponse.class),
                anyMap()
        )).thenReturn(mockResponseEntity);

        // Test input values
        String country = "Italy";
        Integer numOfCities = 10;

        // Invoke the method
        CityResponse result = cityService.getTopCitiesByPopulation(country, numOfCities);

        // Assert the expected result
        assertEquals(mockCityResponse, result);
    }
    //}

    @Test
    void getCountryInformation() {
    }

    @Test
    void getCountryISO() {

        ResponseEntity<IsoResponse> mockResponseEntity = new ResponseEntity<>(isoResponse, HttpStatus.OK);//result of API call

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(IsoResponse.class),
                anyMap()
        )).thenReturn(mockResponseEntity);

        //call the service
       String returnedResponse = cityService.getCountryISO("Nigeria");

       assertNotNull(returnedResponse);


    }

    @Test
    void getCountryCurrency() {

        ResponseEntity<CurrencyResponse> mockResponseEntity = new ResponseEntity<>(currencyResponse, HttpStatus.OK);//result of API call

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(CurrencyResponse.class),
                anyMap()
        )).thenReturn(mockResponseEntity);

        //call the service
        String returnedResponse = cityService.getCountryCurrency("Nigeria");

        assertNotNull(returnedResponse);
    }

    @Test
    void getCountryLocation() {

        List<Integer> positions = new ArrayList<>();
        positions.add(10);
        positions.add(8);

        ResponseEntity<LocationResponse> mockResponseEntity = new ResponseEntity<>(locationResponse, HttpStatus.OK);//result of API call

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(LocationResponse.class),
                anyMap()
        )).thenReturn(mockResponseEntity);

        //call the service
      List<Integer> returnedPositions = cityService.getCountryLocation("Nigeria");

       assertEquals(positions.get(0),returnedPositions.get(0));

    }

    @Test
    void getCountryCapital() {

        ResponseEntity<CapitalResponse> mockResponseEntity = new ResponseEntity<>(capitalResponse, HttpStatus.OK);//result of API call

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(CapitalResponse.class),
                anyMap()
        )).thenReturn(mockResponseEntity);

        //call the service
        String returnedResponse = cityService.getCountryCapital("Nigeria");

        assertEquals(capitalResponse.getData().getCapital(),returnedResponse);
    }

    @Test
    void getCountryPopulation() {

        ResponseEntity<PopulationResponse> mockResponseEntity = new ResponseEntity<>(populationResponse, HttpStatus.OK);//result of API call

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(PopulationResponse.class),
                anyMap()
        )).thenReturn(mockResponseEntity);

        //call the service
        PopulationResponse returnedResponse = cityService.getCountryPopulation("Nigeria");

        assertEquals(mockResponseEntity.getBody(),returnedResponse);
    }

    @Test
    void getCountryStatesAndCities() {
    }
}