package com.currencyConversion.factorserv.repository;

import com.currencyConversion.factorserv.entity.Country;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<Country, String> {

}
