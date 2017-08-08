package com.theopus.knucaTelegram.service.data;

import com.theopus.knucaTelegram.entity.schedule.Room;

import java.util.Collection;
import java.util.Set;


public interface RoomService extends CustomService<Room> {

    Room saveRooms(Room room);
    Set<Room> saveAll(Collection<Room> rooms);
}
