package com.theopus.knucaTelegram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class KnucaTelegramApplication {

    private static final Logger log = LoggerFactory.getLogger(KnucaTelegramApplication.class);



    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext run = SpringApplication.run(KnucaTelegramApplication.class, args);
        log.info("in main starting");
    }
}

