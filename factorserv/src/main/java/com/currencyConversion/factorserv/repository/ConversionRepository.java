package com.currencyConversion.factorserv.repository;

import com.currencyConversion.factorserv.entity.Conversion;
import com.currencyConversion.factorserv.entity.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface ConversionRepository extends CrudRepository<Conversion, Long> {
    Optional<Conversion> findByCountryEquals(@NonNull Country country);

    Optional<Conversion> findByCountry_CountryCodeEquals(@NonNull String countryCode);

}
