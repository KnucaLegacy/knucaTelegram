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
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Service
public class RoomServiceImpl implements RoomService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private Set<Room> roomCache = new HashSet<>();


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
    public Room saveOne(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public Room getById(long id) {
        return roomRepository.getOne(id);
    }

    @Override
    public Collection<Room> getAll() {
        return roomRepository.findAll();
    }

    @Override
    public long getCount() {
        return roomRepository.count();
    }

    @Override
    public void flush() {
        roomCache = null;
    }

    @Override
    public Set<Room> saveAll(Collection<Room> rooms) {
        return new HashSet<>(roomRepository.save(rooms));
    }
}
