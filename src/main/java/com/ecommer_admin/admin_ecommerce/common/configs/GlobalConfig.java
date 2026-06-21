package com.ecommer_admin.admin_ecommerce.common.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalConfig {

    @Bean
    public ModelMapper modelMapper () {
        return new ModelMapper();
    }
}
