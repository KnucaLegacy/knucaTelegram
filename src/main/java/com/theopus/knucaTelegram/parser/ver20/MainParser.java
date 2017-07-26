package com.theopus.knucaTelegram.parser.ver20;

import com.theopus.knucaTelegram.data.entity.Lesson;
import com.theopus.knucaTelegram.data.entity.proxy.LessonProxy;
import com.theopus.knucaTelegram.parser.objects.DayLessonListSheet;
import com.theopus.knucaTelegram.parser.objects.FilePDF;
import com.theopus.knucaTelegram.parser.objects.GroupSheet;
import com.theopus.knucaTelegram.parser.objects.LessonLineSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MainParser {

    private static final Logger log = LoggerFactory.getLogger(MainParser.class);

    public static List<LessonProxy> parseFile(String file) throws IOException {
        FilePDF pdf = new FilePDF(file);
        return parse(pdf);
    }
    public static List<LessonProxy> parseFolder(String folderPath) throws IOException {
        File folder = new File(folderPath);
        Set<FilePDF> pdfFiles = new HashSet<>();
        for (File file : folder.listFiles()){
            pdfFiles.add(new FilePDF(file));
        }
        return parse(pdfFiles.toArray(new FilePDF[pdfFiles.size()]) );
    }

    private static List<LessonProxy> parse(FilePDF... pdfs) throws IOException {
        Set<FilePDF> pdfFiles = new HashSet<>();
        for (FilePDF pdf : pdfs) {
            pdfFiles.add(pdf);
        }
        Set<GroupSheet> sheets = parsePDFFiles(pdfFiles);
        Set<DayLessonListSheet> sheets1 = parseGroupSheets(sheets);
        Set<LessonLineSheet> sheets2 = parseDayLessonListSheet(sheets1);
        List<LessonProxy> lessons = parseLessonLineSheets(sheets2);
        log.info(lessons.size() + " - Lessons parsed.");

        Set<LessonProxy> lessonProxies = mergeDuplicatesArrayAndArray(lessons);
        System.out.println(lessonProxies.size() + "after.");
        lessonProxies = mergeDuplicatesArrayAndArray(lessonProxies);
        System.out.println(lessonProxies.size() + "after.");
        lessonProxies = mergeDuplicatesArrayAndArray(lessonProxies);
        System.out.println(lessonProxies.size() + "after.");
        lessonProxies = mergeDuplicatesArrayAndArray(lessonProxies);
        System.out.println(lessonProxies.size() + "after.");
        lessonProxies = mergeDuplicatesArrayAndArray(lessonProxies);
        System.out.println(lessonProxies.size() + "after.");
        lessonProxies = mergeDuplicatesArrayAndArray(lessonProxies);
        System.out.println(lessonProxies.size() + "after.");
        lessonProxies = mergeSameCollectionToCollection(lessonProxies);
        System.out.println(lessonProxies.size() + " after mergeSame");
        lessonProxies = mergeSameCollectionToCollection(lessonProxies);
        System.out.println(lessonProxies.size() + " after mergeSame");
        lessonProxies = mergeSameCollectionToCollection(lessonProxies);
        System.out.println(lessonProxies.size() + " after mergeSame");
        lessonProxies = mergeSameCollectionToCollection(lessonProxies);
        System.out.println(lessonProxies.size() + " after mergeSame");
        lessonProxies = mergeSameCollectionToCollection(lessonProxies);
        System.out.println(lessonProxies.size() + " after mergeSame");
        lessonProxies = mergeSameCollectionToCollection(lessonProxies);
        System.out.println(lessonProxies.size() + " after mergeSame");
        lessonProxies = mergeSameCollectionToCollection(lessonProxies);
        System.out.println(lessonProxies.size() + " after mergeSame");

        BufferedWriter writer = new BufferedWriter(new FileWriter("trash"));
        trash.forEach(lessonProxy -> {
            try {
                writer.write(lessonProxy.toString());
                writer.write("\n\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        BufferedWriter writer2 = new BufferedWriter(new FileWriter("lessons6755.txt"));
        lessonProxies.forEach(lessonProxy -> {
            try {
                writer2.write(lessonProxy.toString());
                writer2.write("\n\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return lessons;
    }

    private static Set<LessonProxy> trash = new HashSet<>();

    private static Set<LessonProxy> mergeDuplicatesSetAndSet(Set<LessonProxy> lessonProxies){
        Set<LessonProxy> lessons = new LinkedHashSet<>();

        for (LessonProxy lesson1 : lessonProxies) {
            for (LessonProxy lesson2 : lessonProxies) {
                LessonProxy[] lpres = QualityControl.qualityDuplicateControl(lesson1, lesson2);
                if (lpres != null){
                    Collections.addAll(lessons, lpres);
                }
            }
            if (lesson1.getOwnerGroup() != QualityControl.TRASH){
                lessons.add(lesson1);
            }

        }
        return lessons;
    }
    private static Set<LessonProxy> mergeSameCollectionToCollection(Collection<LessonProxy> lessonProxies){
        Set<LessonProxy> lessons = new LinkedHashSet<>();
        for (LessonProxy lesson1 : lessonProxies) {
            if (lesson1.getOwnerGroup() == QualityControl.TRASH){
                trash.add(lesson1);
                continue;
            }
            for (LessonProxy lesson2 :lessonProxies) {
                LessonProxy px = QualityControl.mergeSame(lesson1,lesson2);
                if (px != null)
                    lessons.add(px);
            }
            if (lesson1.getOwnerGroup() != QualityControl.TRASH){
                lessons.add(lesson1);
            }
            else {
                trash.add(lesson1);
            }
        }
        return lessons;
    }
    private static Set<LessonProxy> mergeDuplicatesArrayAndArray(Collection<LessonProxy> lessonProxies){
        Set<LessonProxy> lessons = new LinkedHashSet<>();

        for (LessonProxy lesson1 : lessonProxies) {
            if (lesson1.getOwnerGroup() == QualityControl.TRASH){
                trash.add(lesson1);
                continue;
            }
            for (LessonProxy lesson2 : lessonProxies) {
                LessonProxy[] lpres = QualityControl.qualityDuplicateControl(lesson1, lesson2);
                if (lpres != null){
                    Collections.addAll(lessons, lpres);
                }
            }
            if (lesson1.getOwnerGroup() != QualityControl.TRASH){
                lessons.add(lesson1);
            }

        }
        return lessons;
    }
    private static Set<LessonProxy> mergeDuplicatesArrayAndSet(List<LessonProxy> lessonList, Set<LessonProxy> lessonSet){
        Set<LessonProxy> lessons = new LinkedHashSet<>();

        for (LessonProxy lesson1 : lessonList) {
            for (LessonProxy lesson2 : lessonSet) {
                LessonProxy[] lpres = QualityControl.qualityDuplicateControl(lesson1, lesson2);
                if (lpres != null){
                    Collections.addAll(lessons, lpres);
                }
            }
            if (lesson1.getOwnerGroup() != QualityControl.TRASH){
                lessons.add(lesson1);
            }

        }
        return lessons;
    }

    private static Set<GroupSheet> parsePDFFiles(Set<FilePDF> pdfFiles){
        log.info(pdfFiles.size() + " PDF Files to process.");
        Set<GroupSheet> result = new LinkedHashSet<>();
        pdfFiles.stream().map(FilePDF::parse).forEach(result::addAll);
        log.info("Done.");
        return result;
    }
    private static Set<DayLessonListSheet> parseGroupSheets(Set<GroupSheet> sheets){
        log.info(sheets.size() + "Group sheets to process.");
        Set<DayLessonListSheet> result = new LinkedHashSet<>();
        sheets.stream().map(GroupSheet::parse).forEach(result::addAll);
        log.info("Done.");
        return result;
    }
    private static Set<LessonLineSheet> parseDayLessonListSheet(Set<DayLessonListSheet> dLsehets){
        log.info(dLsehets.size() + " Day Lesson Lists to process.");
        Set<LessonLineSheet> result = new LinkedHashSet<>();
        dLsehets.stream().map(DayLessonListSheet::parse).forEach(result::addAll);
        log.info("Done.");
        return result;
    }
    private static List<LessonProxy> parseLessonLineSheets(Set<LessonLineSheet> lessonLineSheets){
        log.info(lessonLineSheets.size() + " Lesson Lines to process.");
        List<LessonProxy> result = new ArrayList<>();
        for (LessonLineSheet lessonLineSheet : lessonLineSheets) {
           result.add(lessonLineSheet.parse());
        }
        log.info("Done.");
        return result;
    }
}
