package com.ecommer_admin.admin_ecommerce.common.configs;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    @Qualifier("getEmployeeServiceRestClient")
    public RestClient getEmployeeServiceRestClient() {
        return RestClient.builder()
                .baseUrl("https://fake-json-api.mock.beeceptor.com/")
                .defaultHeader("Accept", "application/json")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}
