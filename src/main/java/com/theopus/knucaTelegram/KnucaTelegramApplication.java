package com.theopus.knucaTelegram;

import com.theopus.knucaTelegram.parser.core.MainParser;
import com.theopus.knucaTelegram.service.data.LessonService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
//@ComponentScan(value = "com.theopus.knucaTelegram")
public class KnucaTelegramApplication {


    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext run = SpringApplication.run(KnucaTelegramApplication.class, args);
//        run.getBean(LessonService.class).saveAll(MainParser.parseFolder("pdfs"));
    }
}
