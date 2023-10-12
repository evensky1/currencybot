package com.test.currencybot.model;

import java.util.List;

public record MexcRates(
        List<CurrencyInfo> currencyInfoList
) {
}
