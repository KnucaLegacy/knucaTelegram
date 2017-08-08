package com.theopus.knucaTelegram.parser.core;

import com.theopus.knucaTelegram.entity.schedule.Circumstance;
import com.theopus.knucaTelegram.entity.schedule.Lesson;
import com.theopus.knucaTelegram.parser.objects.entity.LessonProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MainParser {

    private static final Logger log = LoggerFactory.getLogger(MainParser.class);


    public static Set<Lesson> parseFolder(String folderPath) throws IOException {
        log.info("start finding files");
        File folder = new File(folderPath);
        Set<File> pdfFiles = new HashSet<>();
        for (File file : folder.listFiles()){
            log.info("file - " + file.getName());
            pdfFiles.add(file);
        }
        return parse(pdfFiles);
    }

    private static Lesson proxyToObj(LessonProxy lp){
        Lesson lesson = new Lesson();
        lesson.setLessonType(lp.getLessonType());
        lesson.setSubject(lp.getSubject());
        lesson.setGroups(new HashSet<>(lp.getGroups()));
        lesson.setTeachers(new HashSet<>(lp.getTeachers()));
        Set<Circumstance> circumstances = new HashSet<>();
        lp.getRoomDateProxies().forEach(roomDateProxy -> {
            circumstances.add(new Circumstance(
                    roomDateProxy.getOrder(),
                    roomDateProxy.getRoom(),
                    new HashSet<>(roomDateProxy.getDates())
                    ));
        });
        lesson.setCircumstances(circumstances);
        return lesson;
    }

    public static Set<Lesson> parse(Set<File> pdfFiles) throws IOException {
        log.info("start parisng files");
        List<LessonProxy> lessonProxies = new FIleParser(pdfFiles).parse();
        log.info("parsed files and got" + lessonProxies.size());
        Set<LessonProxy> res = new QualityControl(lessonProxies).process();



        System.out.println(res.size());

        BufferedWriter writer2 = new BufferedWriter(new FileWriter("lessons9099.txt"));
        res.forEach(lessonProxy -> {
            try {
                writer2.write(lessonProxy.toString());
                writer2.write("\n\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Set<Lesson> lessons = new HashSet<>();
        res.forEach(lessonProxy -> {
            lessons.add(proxyToObj(lessonProxy));
        });
        return lessons;
    }


}
