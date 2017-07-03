package com.theopus.knucaTelegram;

import com.theopus.knucaTelegram.entity.Group;
import com.theopus.knucaTelegram.entity.Lesson;
import com.theopus.knucaTelegram.entity.Teacher;
import com.theopus.knucaTelegram.entity.utils.KNUCAUtil;
import com.theopus.knucaTelegram.parser.FolderParser;
import com.theopus.knucaTelegram.service.datasevice.LessonService;
import com.theopus.knucaTelegram.service.massupload.MassUploadLessonService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class KnucaTelegramApplication {



    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(KnucaTelegramApplication.class, args);
        run.getBean(FolderParser.class).parseFolder("pdfs");
        Lesson lesson1 = new Lesson();
        lesson1.addGroup(new Group("test-123"));
//        lesson1.addTeacher(new Teacher("loh"));
        Lesson lesson2 = new Lesson();
        lesson2.addGroup(new Group("test-123"));
//        lesson2.addTeacher(new Teacher("тыхуй"));

//        List<Lesson> lessons = new ArrayList<>();
//        lessons.add(lesson1);
//        lessons.add(lesson2);
//        run.getBean(MassUploadLessonService.class).saveAll(lessons);
    }
}
