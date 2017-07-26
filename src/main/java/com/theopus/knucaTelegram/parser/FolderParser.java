package com.theopus.knucaTelegram.parser;

import com.theopus.knucaTelegram.data.entity.Lesson;
import com.theopus.knucaTelegram.parser.ver20.ParserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;

//TODO: REFACTOR IT
public class FolderParser {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public List<Lesson> parseFolder(String path){
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
        ParserUtils.print(lessonList);
        return lessonList;
    }
}
