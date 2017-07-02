package com.theopus.knucaTelegram.repository;

import com.theopus.knucaTelegram.entity.Room;
import com.theopus.knucaTelegram.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    @Query("select t from Teacher t where t.name = :name")
    Teacher findByName(@Param("name") String name);

}
