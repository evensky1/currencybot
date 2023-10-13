package com.test.currencybot.model;

public record ChangedRate(
        String name,
        Double prevPrice,
        Double currentPrice,
        Double percent
) implements Comparable<ChangedRate> {

    @Override
    public int compareTo(ChangedRate o) {
        return Double.compare(Math.abs(o.percent), Math.abs(this.percent));
    }
}
