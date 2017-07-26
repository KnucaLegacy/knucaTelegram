package com.theopus.knucaTelegram.parser.objects;

import com.theopus.knucaTelegram.data.entity.Lesson;
import org.junit.Test;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class FilePDFTest {
    @Test
    public void parse() throws Exception {
        FilePDF filePDF = new FilePDF("pdfs/BB.pdf");
        Set<GroupSheet> sheets = filePDF.parse();

        int size = sheets.size();
        int delta = size/10;
        System.out.println(sheets.size() + " Groupsheets to process.");
        Set<DayLessonListSheet> dayLessonListSheets = new LinkedHashSet<>();

        int i = 0;
        for (GroupSheet sheet : sheets) {
            if (delta != 0)
                if (i % delta == 0)
                    System.out.print(".");
            dayLessonListSheets.addAll(sheet.parse());
            i++;
        }

        size = dayLessonListSheets.size();
        delta = size/10;
        System.out.println(dayLessonListSheets.size() + " daylesson lists to process.");
        Set<LessonLineSheet> lessonLineSheets = new LinkedHashSet<>();

        i = 0;
        for (DayLessonListSheet dayLessonListSheet : dayLessonListSheets) {
            if (delta != 0)
                if (i % delta == 0)
                    System.out.print(".");
            lessonLineSheets.addAll(dayLessonListSheet.parse());
            i++;
        }
        System.out.println("done");
        size = lessonLineSheets.size();
        delta = size/10;
        System.out.println(lessonLineSheets.size() + " lessonlines to process.");
        Map<String,Lesson> lessons = new LinkedHashMap<>();


    }

    @Test
    public void hui() throws Exception {
        String s = "хуй хуй";

        Matcher m = Pattern.compile("хуй").matcher(s);
        while (m.find())
            System.out.println(m.group(0));
    }
}