package com.theopus.knucaTelegram.service.data.repository;

import com.theopus.knucaTelegram.entity.schedule.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface RoomRepository extends PagingAndSortingRepository<Room, Long> {

    @Query("select r from Room r where r.name = :name")
    Room findByName(@Param("name") String name);

}
