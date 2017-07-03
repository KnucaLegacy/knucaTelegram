package com.theopus.knucaTelegram.service.massupload;

import com.theopus.knucaTelegram.entity.Room;

import java.util.Set;

/**
 * Created by irina on 03.07.17.
 */
public interface MassUploadRoomService {

    Set<Room> saveAll(Set<Room> rooms);
}
