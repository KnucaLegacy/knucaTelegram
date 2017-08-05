package com.theopus.knucaTelegram.service.data.repository;

import com.theopus.knucaTelegram.entity.Group;
import com.theopus.knucaTelegram.entity.Lesson;
import com.theopus.knucaTelegram.entity.Subject;
import com.theopus.knucaTelegram.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query( "select distinct l " +
            "from Lesson l " +
            "inner join l.circumstances c " +
            "where (:group_p member of l.groups) and (:date member of c.dates)")
    List<Lesson> getByGroup(@Param("group_p") Group group, @Param("date") Date date);

    @Query( "select distinct l " +
            "from Lesson l " +
            "inner join l.circumstances c " +
            "where (:teacher member of l.teachers) and (:date member of c.dates)")
    List<Lesson> getByTeacher(@Param("teacher") Teacher teacher, @Param("date") Date date);

    @Query( "select distinct l " +
            "from Lesson l " +
            "where l.subject in :subject and :group_p member of l.groups")
    List<Lesson> getByGroupSubject(@Param("group_p") Group group, @Param("subject")Subject subject);

}
