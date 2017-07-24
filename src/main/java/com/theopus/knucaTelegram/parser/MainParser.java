package com.theopus.knucaTelegram.parser;

import com.theopus.knucaTelegram.data.entity.Lesson;
import com.theopus.knucaTelegram.parser.objects.DayLessonListSheet;
import com.theopus.knucaTelegram.parser.objects.FilePDF;
import com.theopus.knucaTelegram.parser.objects.GroupSheet;
import com.theopus.knucaTelegram.parser.objects.LessonLineSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class MainParser {

    private static final Logger log = LoggerFactory.getLogger(MainParser.class);


    public static List<Lesson> parseFolder(String folderPath) throws IOException {
        File folder = new File(folderPath);
        Set<FilePDF> pdfFiles = new HashSet<>();
        for (File file : folder.listFiles()){
            pdfFiles.add(new FilePDF(file));
        }
        Set<GroupSheet> sheets = parsePDFFiles(pdfFiles);
        Set<DayLessonListSheet> sheets1 = parseGroupSheets(sheets);
        Set<LessonLineSheet> sheets2 = parseDayLessonListSheet(sheets1);
        List<Lesson> lessons = parseLessonLineSheets(sheets2);

        log.info(lessons.size() + " - Lessons parsed.");
        log.info(new HashSet<>(lessons).size() + " - Set lesson.");
        return lessons;
    }

    public static Set<GroupSheet> parsePDFFiles(Set<FilePDF> pdfFiles){
        log.info(pdfFiles.size() + " PDF Files to process.");
        Set<GroupSheet> result = new LinkedHashSet<>();
        pdfFiles.stream().map(FilePDF::parse).forEach(result::addAll);
        log.info("Done.");
        return result;
    }
    public static Set<DayLessonListSheet> parseGroupSheets(Set<GroupSheet> sheets){
        log.info(sheets.size() + "Group sheets to process.");
        Set<DayLessonListSheet> result = new LinkedHashSet<>();
        sheets.stream().map(GroupSheet::parse).forEach(result::addAll);
        log.info("Done.");
        return result;
    }
    public static Set<LessonLineSheet> parseDayLessonListSheet(Set<DayLessonListSheet> dLsehets){
        log.info(dLsehets.size() + " Day Lesson Lists to process.");
        Set<LessonLineSheet> result = new LinkedHashSet<>();
        dLsehets.stream().map(DayLessonListSheet::parse).forEach(result::addAll);
        log.info("Done.");
        return result;
    }
    public static List<Lesson> parseLessonLineSheets(Set<LessonLineSheet> lessonLineSheets){
        log.info(lessonLineSheets.size() + " Lesson Lines to process.");
        List<Lesson> result = new ArrayList<>();
        for (LessonLineSheet lessonLineSheet : lessonLineSheets) {
            Lesson parse = lessonLineSheet.parse();
            if (result.contains(parse)){
                Lesson already = result.get(result.indexOf(parse));
                Lesson newl = parse;
                if (!already.getRoomTimePeriod().equals(newl.getRoomTimePeriod())) {
                    result.remove(already);
                    int size = already.getGroups().size();
                    if (size == newl.getGroups().size()){
                        if (size == 1){
                            log.warn("already - " + already);
                            log.warn("new - " + newl);
                            already.addAllRoomTimePeriod(newl.getRoomTimePeriod());
                            log.info("Sum two - " + already);
                            if (!result.contains(already)){
                                result.add(already);
                            }
                            continue;
                        }
                    }
                    log.warn("already - " + already);
                    log.warn("new - " + newl);
                    already.extractNotOwnerGroups();
                    newl.extractNotOwnerGroups();
                    log.info("Extracted already - " + already);
                    log.info("Extracted new - " + newl);
                    if (!result.contains(already))result.add(already);
                    if (!result.contains(newl))result.add(newl);
                }
            }
            else
                result.add(parse);
        }
        log.info("Done.");
        return result;
    }

    public static void parseFile(String file){

    }
}
