package com.currencyConversion.factorserv.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class ExchangeFactorModel {
    @NonNull
    Type type;
    boolean status;
    String errorMsg;
}

