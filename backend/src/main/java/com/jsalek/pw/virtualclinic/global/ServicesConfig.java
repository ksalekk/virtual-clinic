package com.jsalek.pw.virtualclinic.global;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicesConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }




}
