package com.theopus.knucaTelegram.repository;

import com.theopus.knucaTelegram.entity.Group;
import com.theopus.knucaTelegram.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
