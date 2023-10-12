package com.test.currencybot.model;

public record ChangedRate(
        String name,
        Double prevPrice,
        Double currentPrice,
        Double percent
) {

}
