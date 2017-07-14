package com.theopus.knucaTelegram;

import com.theopus.knucaTelegram.data.entity.Lesson;
import com.theopus.knucaTelegram.data.service.LessonService;
import com.theopus.knucaTelegram.parser.FolderParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.Resource;
import java.util.List;

@SpringBootApplication
//@ComponentScan(value = "com.theopus.knucaTelegram")
public class KnucaTelegramApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(KnucaTelegramApplication.class, args);
    }
}
