package com.test.currencybot.service.impl;

import com.test.currencybot.model.ChangedRate;
import com.test.currencybot.model.CurrencyInfo;
import com.test.currencybot.service.CurrencyService;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final WebClient webClient;

    private Map<String, Double> prevMexcRates;

    @Override
    public Mono<List<ChangedRate>> getChangedRates() {
        return getCurrentMexcRates().map(this::provideChangedRateList);
    }

    @EventListener({ContextRefreshedEvent.class})
    public void init() {
        getCurrentMexcRates().subscribe(rates -> prevMexcRates = rates);
    }

    private Mono<Map<String, Double>> getCurrentMexcRates() {
        return webClient
                .get()
                .uri("https://api.mexc.com/api/v3/ticker/price")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CurrencyInfo>>() {})
                .map(rates -> rates.stream().collect(
                        Collectors.toMap(CurrencyInfo::symbol, CurrencyInfo::price)
                ));
    }

    private List<ChangedRate> provideChangedRateList(Map<String, Double> currentRates) {
        var changedRates = new LinkedList<ChangedRate>();

       currentRates.forEach((key, price) -> {
           var prevPrice = prevMexcRates.get(key);

           if (!prevPrice.equals(price)) {
               var changedRate = new ChangedRate(key, prevPrice, price, prevPrice / price);
               changedRates.add(changedRate);
           }
       });

       prevMexcRates = currentRates;
       return changedRates;
    }
}
