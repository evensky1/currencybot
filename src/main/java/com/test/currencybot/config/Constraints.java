package com.test.currencybot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.constraints")
public class Constraints {
    private Long userCount;
    private Long pollDelay;
}
