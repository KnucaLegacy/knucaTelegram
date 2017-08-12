package com.theopus.knucaTelegram.service.data.repository;

import com.theopus.knucaTelegram.entity.schedule.Group;
import com.theopus.knucaTelegram.entity.schedule.Lesson;
import com.theopus.knucaTelegram.entity.schedule.Subject;
import com.theopus.knucaTelegram.entity.schedule.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface LessonRepository extends PagingAndSortingRepository<Lesson, Long> {


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
