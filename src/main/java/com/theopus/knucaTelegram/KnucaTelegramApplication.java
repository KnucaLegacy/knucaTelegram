package com.theopus.knucaTelegram;

import com.theopus.knucaTelegram.entity.Group;
import com.theopus.knucaTelegram.entity.Lesson;
import com.theopus.knucaTelegram.entity.Teacher;
import com.theopus.knucaTelegram.entity.utils.KNUCAUtil;
import com.theopus.knucaTelegram.parser.FolderParser;
import com.theopus.knucaTelegram.parser.ParserPDF;
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

//        String s = "13:50 Оцiнка нерухомостi(ЗIК) (Практ.з.); [до 26.04 ауд.340]; [ ауд.301] доц. Кузнецова Д.С. ЗIK-41";
//        ParserPDF p = new ParserPDF("pdfs/З_К-3.pdf");
//        KNUCAUtil.print(p.parseLessonList(new ArrayList<>()));
//        System.out.println(p.parseLesson(s,"ЗIK-41",null));
    }
}
