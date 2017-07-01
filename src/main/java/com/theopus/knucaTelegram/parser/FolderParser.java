package com.theopus.knucaTelegram.parser;

import com.theopus.knucaTelegram.entity.Lesson;
import com.theopus.knucaTelegram.entity.utils.KNUCAUtil;
import com.theopus.knucaTelegram.service.LessonService;
import com.theopus.knucaTelegram.service.impl.LessonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class FolderParser {


    @Qualifier("lessonServiceImpl")
    @Autowired
    private LessonService lessonService;

    public void parseFolder(String path){
        File folder = new File(path);
        Set<ParserPDF> parserPDFSet = new HashSet<>();
        for (File file : folder.listFiles()){
            parserPDFSet.add(new ParserPDF(file));
        }

        List<Lesson> lessonList = new ArrayList<>();

        for (ParserPDF parser : parserPDFSet) {
            parser.parseLessonList(lessonList);
        }

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
