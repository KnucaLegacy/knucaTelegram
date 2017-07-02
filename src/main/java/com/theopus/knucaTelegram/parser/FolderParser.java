package com.theopus.knucaTelegram.parser;

import com.theopus.knucaTelegram.entity.Lesson;
import com.theopus.knucaTelegram.entity.utils.KNUCAUtil;
import com.theopus.knucaTelegram.service.LessonService;
import com.theopus.knucaTelegram.service.impl.LessonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;

@Component
public class FolderParser {


    @Qualifier("lessonServiceImpl")
    @Autowired
    private LessonService lessonService;

    public void parseFolder(String path){
        File folder = new File(path);
        Set<ParserPDF> parserPDFSet = new HashSet<>();
        int i = 0;
        for (File file : folder.listFiles()){
            parserPDFSet.add(new ParserPDF(file));
            i++;
        }

        List<Lesson> lessonList = new ArrayList<>();

        for (ParserPDF parser : parserPDFSet) {
            parser.parseLessonList(lessonList);
        }

        long before = new Date().getTime();
        for (Lesson les: lessonList) {
            lessonService.addLesson(les);
        }


        System.out.println("----------------RESULT-----------------");
        System.out.println("----------------RESULT-----------------");
        System.out.println("----------------RESULT-----------------");
        System.out.println("----------------RESULT-----------------");
        System.out.println("----------------RESULT-----------------");
        KNUCAUtil.print(lessonList);
    }
}
