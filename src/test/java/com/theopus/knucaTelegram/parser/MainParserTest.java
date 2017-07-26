package com.theopus.knucaTelegram.parser;

import com.theopus.knucaTelegram.data.entity.proxy.LessonProxy;
import com.theopus.knucaTelegram.parser.ver20.MainParser;
import com.theopus.knucaTelegram.parser.ver20.QualityControl;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class MainParserTest {
    @Test
    public void parseFolder() throws Exception {
        Set<LessonProxy> arrayAndArrayResult = new HashSet<>();
        List<LessonProxy> pdfs20 = MainParser.parseFolder("pdfs");

        System.out.println("array - " + pdfs20.size());
        for (LessonProxy lesson1 : pdfs20) {
            for (LessonProxy lesson2 : pdfs20) {
                LessonProxy[] lessonProxies = QualityControl.qualityDuplicateControl(lesson1, lesson2);
                if (lessonProxies != null){
                    Collections.addAll(arrayAndArrayResult, lessonProxies);
                }
            }
            if (lesson1.getOwnerGroup() != QualityControl.TRASH){
                arrayAndArrayResult.add(lesson1);
            }
        }

        System.out.println("arrayAndArrayresult  - " + arrayAndArrayResult.size());
        Set<LessonProxy> arrayAndSetResult = new LinkedHashSet<>();

        for (LessonProxy lessonArr : pdfs20) {
            for (LessonProxy lessonSet : arrayAndArrayResult) {
                LessonProxy[] lessonProxies = QualityControl.qualityDuplicateControl(lessonArr, lessonSet);
                if (lessonProxies != null){
                    Collections.addAll(arrayAndSetResult, lessonProxies);
                }
            }
            if (lessonArr.getOwnerGroup() != QualityControl.TRASH){
                arrayAndSetResult.add(lessonArr);
            }
        }

        System.out.println("Array and Set result - " + arrayAndSetResult.size());
        Set<LessonProxy> setAndSetResult = new LinkedHashSet<>();

        for (LessonProxy lesson1 : arrayAndSetResult) {
            for (LessonProxy lesson2 : arrayAndSetResult) {
                LessonProxy[] lessonProxies = QualityControl.qualityDuplicateControl(lesson1, lesson2);
                if (lessonProxies != null){
                    Collections.addAll(setAndSetResult, lessonProxies);
                }
            }
            if (lesson1.getOwnerGroup() != QualityControl.TRASH){
                setAndSetResult.add(lesson1);
            }

        }

        System.out.println("set and set result - " + setAndSetResult.size());
        Set<LessonProxy> setAndSetResult2 = new LinkedHashSet<>();

        for (LessonProxy lesson1 : setAndSetResult) {
            for (LessonProxy lesson2 : setAndSetResult) {
                LessonProxy[] lessonProxies = QualityControl.qualityDuplicateControl(lesson1, lesson2);
                if (lessonProxies != null){
                    Collections.addAll(setAndSetResult2, lessonProxies);
                }
            }
            if (lesson1.getOwnerGroup() != QualityControl.TRASH){
                setAndSetResult2.add(lesson1);
            }

        }

        System.out.println("set and set resul2t - " + setAndSetResult2.size());

        Set<LessonProxy> afeterSameMere = new LinkedHashSet<>();
        for (LessonProxy lesson1 : setAndSetResult2) {
            for (LessonProxy lesson2 : setAndSetResult2) {
                LessonProxy px = QualityControl.mergeSame(lesson1,lesson2);
                if (px != null)
                    afeterSameMere.add(px);
            }
            if (lesson1.getOwnerGroup() != QualityControl.TRASH){
                afeterSameMere.add(lesson1);
            }
        }

        System.out.println("afeter same mege - " + afeterSameMere.size());

        Set<LessonProxy> afeterSameMere2 = new LinkedHashSet<>();


        for (LessonProxy lesson1 : afeterSameMere) {
            for (LessonProxy lesson2 : afeterSameMere) {
                LessonProxy px = QualityControl.mergeSame(lesson1,lesson2);
                if (px != null)
                    afeterSameMere2.add(px);
            }
            if (lesson1.getOwnerGroup() != QualityControl.TRASH){
                afeterSameMere2.add(lesson1);
            }
        }
        System.out.println("afeter same mege2 - " + afeterSameMere2.size());
        Set<LessonProxy> afeterSameMere3 = new LinkedHashSet<>();
        for (LessonProxy lesson1 : afeterSameMere2) {
            for (LessonProxy lesson2 : afeterSameMere2) {
                LessonProxy px = QualityControl.mergeSame(lesson1,lesson2);
                if (px != null)
                    afeterSameMere3.add(px);
            }
            if (lesson1.getOwnerGroup() != QualityControl.TRASH){
                afeterSameMere3.add(lesson1);
            }
        }

        System.out.println("afeter same mege3 - " + afeterSameMere3.size());
        Set<LessonProxy> afeterSameMere4 = new LinkedHashSet<>();
        for (LessonProxy lesson1 : afeterSameMere3) {
            for (LessonProxy lesson2 : afeterSameMere3) {
                LessonProxy px = QualityControl.mergeSame(lesson1,lesson2);
                if (px != null)
                    afeterSameMere4.add(px);
            }
            if (lesson1.getOwnerGroup() != QualityControl.TRASH){
                afeterSameMere4.add(lesson1);
            }
        }

        System.out.println("afeter same mege4 - " + afeterSameMere4.size());
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("result")));
        for (LessonProxy lessonProxy : afeterSameMere4) {
            writer.write(lessonProxy.toString());
            writer.write("\n");
        }
        writer.close();

    }

    @Test
    public void parseNew() throws Exception {
        MainParser.parseFolder("pdfs");
    }
}