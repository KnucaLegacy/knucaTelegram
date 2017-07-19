package com.theopus.knucaTelegram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
//@ComponentScan(value = "com.theopus.knucaTelegram")
public class KnucaTelegramApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(KnucaTelegramApplication.class, args);
//        run.getBean(LessonService.class).saveAll(new FolderParser().parseFolder("pdfs"));
    }
}
