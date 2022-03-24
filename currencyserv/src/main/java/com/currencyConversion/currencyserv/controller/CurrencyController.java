package com.currencyConversion.currencyserv.controller;

import com.currencyConversion.currencyserv.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class CurrencyController {

    final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/currency")
    public ResponseEntity<Map<String, String>> convertCurrency(@RequestParam String countryCode, @RequestParam double amount) {

        Optional<Double> response = currencyService.convertCurrency(countryCode, amount);
        if (response.isPresent()) {
            Map<String, String> map = new HashMap<>();
            map.put("Country Code", countryCode);
            map.put("Base Amount", amount + "");
            map.put("Converted Amount", response.get().toString());
            return ResponseEntity.ok(map);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Conversion Factor with Country Code %s not present. Currency Conversion Failed!", countryCode));
        }

    }
}
