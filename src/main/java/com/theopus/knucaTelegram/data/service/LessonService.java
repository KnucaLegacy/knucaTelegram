package com.theopus.knucaTelegram.data.service;

import com.theopus.knucaTelegram.data.entity.Lesson;

import java.util.Date;
import java.util.List;


public interface LessonService {

    List<Lesson> saveAll(List<Lesson> lessons);

    List<Lesson> getAll();

    List<Lesson> getTodayByGroupName(String name);

    List<Lesson> getByExactDayByGroupName(Date date, String name);

    void flush();
}
