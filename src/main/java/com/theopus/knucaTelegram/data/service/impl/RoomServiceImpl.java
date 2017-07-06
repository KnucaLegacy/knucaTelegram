package com.theopus.knucaTelegram.data.service.impl;

import com.theopus.knucaTelegram.data.entity.LessonDate;
import com.theopus.knucaTelegram.data.entity.Room;
import com.theopus.knucaTelegram.data.entity.RoomTimePeriod;
import com.theopus.knucaTelegram.data.repository.RoomRepository;
import com.theopus.knucaTelegram.data.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by irina on 03.07.17.
 */
@Service
public class RoomServiceImpl implements RoomService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private Set<Room> roomCache = new HashSet<>();

    @Override
    public Set<Room> saveAll(Set<Room> rooms) {
        return null;
    }
    @Resource
    private RoomRepository roomRepository;

    public Set<RoomTimePeriod> saveRooms(Set<RoomTimePeriod> rtm){
        for (RoomTimePeriod rtp: rtm) {
            Room r = rtp.getRoom();
            if (roomCache.contains(r)){
                rtp.setRoom(getRoom(r));
            }
            else {
                Room findR = roomRepository.findByName(r.getName());
                if (findR != null) {
                    roomCache.add(findR);
                    rtp.setRoom(findR);
                }
                else {
                    Room saveRoom = roomRepository.save(r);
                    roomCache.add(saveRoom);
                    rtp.setRoom(saveRoom);
                }

            }
                try {
                    for (LessonDate ld : rtp.getLessonDate()) {
                        ld.setRoomTimePeriod(rtp);

                    }
                }catch (NullPointerException e){
                    System.out.println(rtp);
                    e.printStackTrace();
                }

        }
        return rtm;
    }

    private Room getRoom(Room subject){
        for (Room r: roomCache) {
            if (r.equals(subject))
                return r;
        }
        return null;
    }

    @Override
    public void flush() {
        roomCache = null;
    }
}
