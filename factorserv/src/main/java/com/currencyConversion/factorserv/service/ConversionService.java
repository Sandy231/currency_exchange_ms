package com.currencyConversion.factorserv.service;

import com.currencyConversion.factorserv.entity.Conversion;
import com.currencyConversion.factorserv.entity.Country;
import com.currencyConversion.factorserv.model.ExchangeFactorModel;
import com.currencyConversion.factorserv.model.Type;
import com.currencyConversion.factorserv.repository.ConversionRepository;
import com.currencyConversion.factorserv.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Service
public class ConversionService {

    public static final String BASE_COUNTRY = "US";
    final ConversionRepository conversionRepo;
    final CountryRepository countryRepo;

    @Autowired
    public ConversionService(ConversionRepository conversionRepo, CountryRepository countryRepo) {
        this.conversionRepo = conversionRepo;
        this.countryRepo = countryRepo;
    }

    public ExchangeFactorModel addConversionFactor(String countryCode, double conversionFactor) {
        ExchangeFactorModel factorModel = new ExchangeFactorModel(Type.ADD);

        Consumer<Country> ifPresentAction = (country) -> {
            if (countryCode.equals(BASE_COUNTRY)) {
                factorModel.setStatus(false);
                factorModel.setErrorMsg("Base Country conversion factor cannot be added");
            } else {
                Consumer<Conversion> factor = (conversion) -> {
                    factorModel.setStatus(false);
                    factorModel.setErrorMsg("Conversion factor with the given country code " + country.getCountryCode() + " is already present.");
                };
                Runnable addEntry = () -> {
                    Conversion conversion = new Conversion();
                    conversion.setCountry(country);
                    conversion.setFactor(conversionFactor);
                    conversionRepo.save(conversion);
                    factorModel.setStatus(true);
                };
                conversionRepo.findByCountryEquals(country).ifPresentOrElse(factor, addEntry);
            }
        };
        Runnable ifAbsent = () -> {
            factorModel.setStatus(false);
            factorModel.setErrorMsg("This is Invalid Country Code!! Add conversion factor failed.");
        };
        countryRepo.findById(countryCode.trim().toUpperCase()).ifPresentOrElse(ifPresentAction, ifAbsent);
        return factorModel;
    }

    public ExchangeFactorModel updateConversionFactor(String countryCode, double conversionFactor) {
        ExchangeFactorModel factorModel = new ExchangeFactorModel(Type.UPDATE);

        Consumer<Country> ifUpdateAction = (country) -> {
            if (countryCode.equals(BASE_COUNTRY)) {
                factorModel.setStatus(false);
                factorModel.setErrorMsg("Base Country conversion-factor cannot be added or updated");
            } else {
                //Consumer<Country> ifCountryPresent =(country)->;


                Consumer<Conversion> updateFactor = (conversion) -> {
                    conversion.setFactor(conversionFactor);
                    conversionRepo.save(conversion);
                    factorModel.setStatus(true);

                };
                Runnable notPresent = () -> {
                    factorModel.setStatus(false);
                    factorModel.setErrorMsg("Given CountryCode " + countryCode + " is not present in the system. Please add first!");
                };
                conversionRepo.findByCountryEquals(country).ifPresentOrElse(updateFactor, notPresent);
            }
        };
        Runnable ifAbsent = () -> {
            factorModel.setStatus(false);
            factorModel.setErrorMsg("This is Invalid Country Code!! Update conversion factor failed.");
        };
        countryRepo.findById(countryCode.trim().toUpperCase()).ifPresentOrElse(ifUpdateAction, ifAbsent);
        return factorModel;
    }

    public double getConversionFactor(String countryCode) {
        return conversionRepo.findByCountry_CountryCodeEquals(countryCode).orElseThrow().getFactor();
    }

    public Map<String, Double> getAllFactors() {
        Map<String, Double> map = new HashMap<>();
        conversionRepo.findAll().forEach(factor -> map.put(factor.getCountry().getCountryCode(), factor.getFactor()));
        return map;
    }
}
