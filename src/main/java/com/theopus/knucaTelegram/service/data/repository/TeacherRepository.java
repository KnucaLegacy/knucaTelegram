package com.theopus.knucaTelegram.service.data.repository;

import com.theopus.knucaTelegram.entity.schedule.Teacher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface TeacherRepository extends PagingAndSortingRepository<Teacher, Long> {

    @Query("select t from Teacher t where t.name = :name")
    Teacher findByExactName(@Param("name") String name);

    @Query(value = "select t from Teacher t where t.name LIKE concat('%', ?1, '%') ")
    Set<Teacher> findNameAlies(String name);

}
