package com.theopus.knucaTelegram.data.service;

import com.theopus.knucaTelegram.data.entity.Room;
import com.theopus.knucaTelegram.data.entity.RoomTimePeriod;

import java.util.Collection;
import java.util.Set;


public interface RoomService extends CustomService<Room> {

    Set<RoomTimePeriod> saveRooms(Set<RoomTimePeriod> rtm);
    Set<Room> saveAll(Collection<Room> rooms);
}
