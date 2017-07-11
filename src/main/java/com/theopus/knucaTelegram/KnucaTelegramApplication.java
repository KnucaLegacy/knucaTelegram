package com.theopus.knucaTelegram;

import com.theopus.knucaTelegram.data.entity.Lesson;
import com.theopus.knucaTelegram.data.service.LessonService;
import com.theopus.knucaTelegram.parser.FolderParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.annotation.Resource;
import java.util.List;

@SpringBootApplication
public class KnucaTelegramApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(KnucaTelegramApplication.class, args);
        List <Lesson> lessons= new FolderParser().parseFolder("pdfs");
//        System.out.println(lessons);
        run.getBean(LessonService.class).saveAll(lessons);

    }
}
