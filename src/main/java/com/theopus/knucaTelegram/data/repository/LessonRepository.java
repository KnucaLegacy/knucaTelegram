package com.theopus.knucaTelegram.data.repository;

import com.theopus.knucaTelegram.data.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query(value = "select les.* " +
            "FROM lesson les " +
            "INNER JOIN lesson_group_p lg " +
            "ON les.id = lg.lessons_id " +
            "INNER JOIN group_p gro ON lg.groups_id = gro.id " +
            "WHERE (gro.name=?2 AND les.dayofWeek = ?1)", nativeQuery = true)
    List<Lesson> findDayGroupName(int day, String name);


}
