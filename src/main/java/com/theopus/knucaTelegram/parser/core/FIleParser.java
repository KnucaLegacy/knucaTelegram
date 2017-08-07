package com.theopus.knucaTelegram.parser.core;

import com.theopus.knucaTelegram.parser.objects.entity.LessonProxy;
import com.theopus.knucaTelegram.parser.objects.DayLessonListSheet;
import com.theopus.knucaTelegram.parser.objects.FilePDF;
import com.theopus.knucaTelegram.parser.objects.GroupSheet;
import com.theopus.knucaTelegram.parser.objects.LessonLineSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class FIleParser {

    private static final Logger log = LoggerFactory.getLogger(FIleParser.class);

    Set<File> files = new HashSet<>();

    FIleParser(Set<File> files) {
        this.files = files;
    }


    FIleParser(File files) {
        this.files.add(files);
    }


    List<LessonProxy> parse() throws IOException {
        Set<FilePDF> pdfFiles = new HashSet<>();
        for (File file : files) {
            pdfFiles.add(new FilePDF(file));
        }
        Set<GroupSheet> sheets = parsePDFFiles(pdfFiles);
        Set<DayLessonListSheet> sheets1 = parseGroupSheets(sheets);
        Set<LessonLineSheet> sheets2 = parseDayLessonListSheet(sheets1);
        List<LessonProxy> lessons = parseLessonLineSheets(sheets2);
        log.info(lessons.size() + " - Lessons parsed.");
        return lessons;
    }

    private Set<GroupSheet> parsePDFFiles(Set<FilePDF> pdfFiles){
        log.info(pdfFiles.size() + " PDF Files to process.");
        Set<GroupSheet> result = new LinkedHashSet<>();
        pdfFiles.stream().map(FilePDF::parse).forEach(result::addAll);
        log.info("Done.");
        return result;
    }
    private Set<DayLessonListSheet> parseGroupSheets(Set<GroupSheet> sheets){
        log.info(sheets.size() + "GroupProxy sheets to process.");
        Set<DayLessonListSheet> result = new LinkedHashSet<>();
        sheets.stream().map(GroupSheet::parse).forEach(result::addAll);
        log.info("Done.");
        return result;
    }
    private Set<LessonLineSheet> parseDayLessonListSheet(Set<DayLessonListSheet> dLsehets){
        log.info(dLsehets.size() + " Day Lesson Lists to process.");
        Set<LessonLineSheet> result = new LinkedHashSet<>();
        dLsehets.stream().map(DayLessonListSheet::parse).forEach(result::addAll);
        log.info("Done.");
        return result;
    }
    private List<LessonProxy> parseLessonLineSheets(Set<LessonLineSheet> lessonLineSheets){
        log.info(lessonLineSheets.size() + " Lesson Lines to process.");
        List<LessonProxy> result = new ArrayList<>();
        for (LessonLineSheet lessonLineSheet : lessonLineSheets) {
            result.add(lessonLineSheet.parse());
        }
        log.info("Done.");
        return result;
    }
}
