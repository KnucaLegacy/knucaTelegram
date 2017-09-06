package com.theopus.knucaTelegram.service.data.impl;

import com.theopus.knucaTelegram.entity.schedule.Room;
import com.theopus.knucaTelegram.service.data.repository.RoomRepository;
import com.theopus.knucaTelegram.service.data.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class RoomServiceImpl implements RoomService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private Set<Room> roomCache = new HashSet<>();


    @Resource
    private RoomRepository roomRepository;

    private Room getRoom(Room subject){
        for (Room r: roomCache) {
            if (r.equals(subject))
                return r;
        }
        return null;
    }

    @Override
    public Room saveOne(Room room) {
        Room result;
        if (roomCache.contains(room))
            result = getRoom(room);
        else {
            Room findR = roomRepository.findByName(room.getName());
            if (findR != null){
                result = findR;
                roomCache.add(findR);
            }
            else{
                Room savedR = roomRepository.save(room);
                result = savedR;
                roomCache.add(savedR);
            }

        }
        return result;
    }

    @Override
    public Room getById(long id) {
        return roomRepository.findOne(id);
    }

    @Override
    public Collection<Room> getAll() {
        Collection<Room> result = new HashSet<>();
        roomRepository.findAll().forEach(result::add);
        return result;
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
    public void deleteBy(long id) {

    }

    @Override
    public void deleteBy(Room room) {

    }

    @Override
    public Set<Room> saveAll(Collection<Room> rooms) {
        return rooms.stream().map(this::saveOne).collect(Collectors.toSet());
    }
}
