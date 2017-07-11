package com.theopus.knucaTelegram.data.repository;

import com.theopus.knucaTelegram.data.entity.Group;
import com.theopus.knucaTelegram.data.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    @Query("select t from Teacher t where t.name = :name")
    Teacher findByExactName(@Param("name") String name);

    @Query(value = "select t from Teacher t where t.name LIKE concat('%', ?1, '%') ")
    Set<Teacher> findNameAlies(String name);

}
