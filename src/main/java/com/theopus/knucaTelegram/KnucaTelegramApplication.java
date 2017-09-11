package com.theopus.knucaTelegram;

import com.theopus.knucaTelegram.entity.schedule.Lesson;
import com.theopus.knucaTelegram.parser.core.MainParser;
import com.theopus.knucaTelegram.service.data.LessonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.annotation.PostConstruct;
import java.io.IOException;

@SpringBootApplication
public class KnucaTelegramApplication {

    private static final Logger log = LoggerFactory.getLogger(KnucaTelegramApplication.class);


    @PostConstruct
    void started() {

    }


    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext run = SpringApplication.run(KnucaTelegramApplication.class, args);
        log.info("in main starting");
//        run.getBean(LessonService.class).saveAll(MainParser.parseFolder("pdfs"));

    }
}
