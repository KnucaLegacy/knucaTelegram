package com.theopus.knucaTelegram.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Configuration
public class AdditionalConfig {

    @Bean
    public DateFormat getDateFormat(){
        return new SimpleDateFormat("dd-MM-yyyy");
    }
}
