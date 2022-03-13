package com.currencyConversion.factorserv.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@Entity
@ToString(onlyExplicitlyIncluded = true)
@Getter
@Setter
public class Conversion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
    @JoinColumn(name = "country_code", nullable = false, unique = true)
    private Country country;

    @Column(name = "factor", nullable = false)
    private double factor;
}
