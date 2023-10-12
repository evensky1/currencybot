package com.test.currencybot.service;

import com.test.currencybot.model.ChangedRate;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CurrencyService {

    Mono<List<ChangedRate>> getChangedRates();
}
