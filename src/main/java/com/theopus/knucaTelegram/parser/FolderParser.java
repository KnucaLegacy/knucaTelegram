package com.theopus.knucaTelegram.parser;

import com.theopus.knucaTelegram.data.entity.Lesson;
import com.theopus.knucaTelegram.data.service.LessonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;

@Component
public class FolderParser {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
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
        ParserPDF.print(lessonService.saveAll(lessonList));
        System.out.println("DDDDDDDDDDDDDDDDDDDDDD");
        System.out.println("DDDDDDDDDDDDDDDDDDDDDD");
        System.out.println("DDDDDDDDDDDDDDDDDDDDDD");
        System.out.println("DDDDDDDDDDDDDDDDDDDDDD");
        System.out.println("DDDDDDDDDDDDDDDDDDDDDD");
        ParserPDF.print(lessonService.getAll());
    }
}
