package com.theopus.knucaTelegram.data.repository;

import com.theopus.knucaTelegram.data.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query("select g from Group g where g.name = :name")
    Group findByName(@Param("name") String name);
}
