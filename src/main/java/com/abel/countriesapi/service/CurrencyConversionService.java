package com.abel.countriesapi.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CurrencyConversionService {

    private final Map<String, Map<String, BigDecimal>> exchangeRates;

    public CurrencyConversionService() {
        this.exchangeRates = loadExchangeRates();
    }

    private Map<String, Map<String, BigDecimal>> loadExchangeRates() {
        Map<String, Map<String, BigDecimal>> rates = new HashMap<>();
        try (CSVReader reader = new CSVReader(new FileReader("exchange_rate.csv"))) {
            List<String[]> rows = reader.readAll();
            for (String[] row : rows) {
                String baseCurrency = row[0];
                String targetCurrency = row[1];
                BigDecimal exchangeRate = new BigDecimal(row[2]);

                rates.computeIfAbsent(baseCurrency, k -> new HashMap<>())
                        .put(targetCurrency, exchangeRate);
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        return rates;
    }

    public BigDecimal convertCurrency(String baseCurrency, String targetCurrency, BigDecimal amount) {
        if (baseCurrency.equals(targetCurrency)) {
            return amount;
        }

        Map<String, BigDecimal> baseCurrencyRates = exchangeRates.get(baseCurrency);
        if (baseCurrencyRates == null) {
            throw new IllegalArgumentException("Base currency not found: " + baseCurrency);
        }

        BigDecimal exchangeRate = baseCurrencyRates.get(targetCurrency);
        if (exchangeRate == null) {
            throw new IllegalArgumentException("Target currency not found: " + targetCurrency);
        }

        return amount.multiply(exchangeRate);
    }
}
