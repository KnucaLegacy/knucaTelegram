package com.theopus.knucaTelegram.repository;

import com.theopus.knucaTelegram.entity.Group;
import com.theopus.knucaTelegram.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("select r from Room r where r.name = :name")
    Room findByName(@Param("name") String name);

}
