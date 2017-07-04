package com.theopus.knucaTelegram.data.service;

import com.theopus.knucaTelegram.data.entity.Lesson;

import java.util.List;

/**
 * Created by irina on 03.07.17.
 */

public interface LessonService {

    List<Lesson> saveAll(List<Lesson> lessons);

    List<Lesson> getAll();

    List<Lesson> getByWeekDayGroupName(int day, String name);

    void flush();
}
