package com.theopus.knucaTelegram.service.data.repository;

import com.theopus.knucaTelegram.entity.schedule.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface GroupRepository extends PagingAndSortingRepository<Group, Long> {

    @Query("select g from Group g where g.name = :name")
    Group findByName(@Param("name") String name);

    @Query(value = "select g from Group g where g.name LIKE concat('%', ?1, '%') ")
    Set<Group> findNameAlies(String name);

    @Query(value = "select g from Group g where g.name LIKE concat('%', ?1, '%') ")
    Page<Group> findNameAliesPaged(String name, Pageable pageable);

}
