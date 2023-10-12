package com.test.currencybot;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class CurrencybotApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencybotApplication.class, args);
	}

}
