package com.theopus.knucaTelegram.data.repository;

import com.theopus.knucaTelegram.data.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query("select g from Group g where g.name = :name")
    Group findByName(@Param("name") String name);

    @Query(value = "select g from Group g where g.name LIKE concat('%', name, '%') ")
    Set<Group> findNameAlies(@Param("name") String name);


}
