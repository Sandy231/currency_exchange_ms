package com.currencyConversion.factorserv.factorController;

import com.currencyConversion.factorserv.model.ExchangeFactorModel;
import com.currencyConversion.factorserv.service.ConversionService;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping
public class FactorServController {
    @Autowired
    ConversionService conversionService;

    @PostMapping("/factor")
    public ResponseEntity<String> addConversionFactor(@RequestBody Map<String, String> addFactorInput) {
        ExchangeFactorModel factorModel = conversionService.addConversionFactor(addFactorInput.get("countryCode"), Double.parseDouble(addFactorInput.get("conversionFactor")));
        if (factorModel.isStatus()) {
            return ResponseEntity.ok().body("Factor added successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(factorModel.getErrorMsg());
        }
    }

    @PutMapping("/factor")
    public ResponseEntity<String> updateConversionFactor(@RequestBody Map<String, String> updateFactorInput) {
        ExchangeFactorModel factorModel = conversionService.updateConversionFactor(updateFactorInput.get("countryCode"), Double.parseDouble(updateFactorInput.get("conversionFactor")));
        if (factorModel.isStatus()) {
            return ResponseEntity.ok().body("Factor Updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(factorModel.getErrorMsg());
        }
    }

    @GetMapping("/factor/{code}")
    public ResponseEntity<ImmutablePair<String,Double>> getConversionFactor(@PathVariable String code) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new ImmutablePair<>(code, conversionService.getConversionFactor(code)));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Conversion Factor with given CC " + code + " not found!");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conversion Factor with given CC " + code + " not found!");
        }
    }

    @GetMapping("/factor")
    public ResponseEntity<Map<String, Double>> getAllConversionFactors() {
        return ResponseEntity.ok().body(conversionService.getAllFactors());
    }
}