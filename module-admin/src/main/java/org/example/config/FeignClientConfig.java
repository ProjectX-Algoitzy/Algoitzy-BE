package org.example.config;

import feign.RequestInterceptor;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Level.ALL;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Accept", "application/json");
        };
    }
}
