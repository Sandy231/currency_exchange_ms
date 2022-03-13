package com.currencyConversion.factorserv.component;


import com.currencyConversion.factorserv.entity.Country;
import com.currencyConversion.factorserv.repository.CountryRepository;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
public class Loader {

    private CountryRepository countryRepository;

    @Autowired
    Loader(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @PostConstruct
    private void loadData() {
        InputStream fileStream = getClass().getResourceAsStream("/country_list.csv");
        CSVReader reader = new CSVReader(new InputStreamReader(fileStream));
        reader.forEach(record -> {
            Country country = new Country();
            country.setCountryCode(record[1].trim().toUpperCase());
            country.setCountryName(record[0].trim().toUpperCase());
            country.setCurrency(record[2].trim());
            //country.setCurrencyCode(record[3].trim().toUpperCase());
            countryRepository.save(country);
        });
    }
}
