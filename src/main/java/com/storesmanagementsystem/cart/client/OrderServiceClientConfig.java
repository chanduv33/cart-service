package com.storesmanagementsystem.cart.client;

import feign.Logger;
import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderServiceClientConfig {
    @Bean
    public OkHttpClient getOrderClient(){
        return new OkHttpClient();
    }

    @Bean
    Logger.Level orderLoggerLevel() {
        return Logger.Level.FULL;
    }
}
