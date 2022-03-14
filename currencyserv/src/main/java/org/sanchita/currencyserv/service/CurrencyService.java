package org.sanchita.currencyserv.service;

import java.util.Optional;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyService {

    @Value("${serv.factorservice.name}")
    private String factorService;

    @Autowired
    RestTemplate restTemplate;

    public Optional<Double> convertCurrency(String countryCode, double amount){

        String factorServiceUrl = String.format("http://%s/factor/{countryCode}", factorService);

        try{
            ResponseEntity<JsonNode> factorEntity = restTemplate.getForEntity(factorServiceUrl,JsonNode.class, countryCode.trim().toUpperCase());
            if(factorEntity.getStatusCode()== HttpStatus.OK){
                return Optional.of(amount * factorEntity.getBody().get(countryCode.trim().toUpperCase()).asDouble());
            }else{
                return Optional.empty();
            }
        }catch (HttpClientErrorException.NotFound e){
            return Optional.empty();
        }
    }
    
}
