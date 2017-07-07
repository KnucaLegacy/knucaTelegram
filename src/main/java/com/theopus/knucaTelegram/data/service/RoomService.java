package com.theopus.knucaTelegram.data.service;

import com.theopus.knucaTelegram.data.entity.Room;

import java.util.Set;


public interface RoomService {

    Set<Room> saveAll(Set<Room> rooms);
    void flush();
}
