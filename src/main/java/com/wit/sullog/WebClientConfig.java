package com.wit.sullog;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class WebClientConfig {

	@Bean
    public WebClient webClient(){
        // REST 통신을 위한 API 등록
        return WebClient.create();
    }
}
