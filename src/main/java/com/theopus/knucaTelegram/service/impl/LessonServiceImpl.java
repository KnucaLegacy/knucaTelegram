package com.theopus.knucaTelegram.service.impl;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.theopus.knucaTelegram.entity.Lesson;
import com.theopus.knucaTelegram.repository.LessonRepository;
import com.theopus.knucaTelegram.service.LessonService;
import org.hibernate.dialect.MySQL5Dialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    LessonRepository lessonRepository;

    @Override
    public Lesson addLesson(Lesson lesson) {
        try {
            Lesson tmp = lessonRepository.saveAndFlush(lesson);
        }
        catch (Exception ex) {
            System.out.println(ex.getCause().getCause().getCause().getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public Lesson getByName(String name) {
        return null;
    }

    @Override
    public Lesson editBank(Lesson bank) {
        return null;
    }

    @Override
    public List<Lesson> getAll() {
        return null;
    }
}
