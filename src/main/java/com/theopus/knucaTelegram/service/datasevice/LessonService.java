package com.theopus.knucaTelegram.service.datasevice;

import com.theopus.knucaTelegram.entity.Lesson;

import java.util.List;

public interface LessonService {

    Lesson addLesson(Lesson lesson);
    void delete(long id);
    Lesson getByName(String name);
    Lesson editBank(Lesson bank);
    List<Lesson> getAll();

}
