package com.theopus.knucaTelegram.data.entity.proxy;

import com.theopus.knucaTelegram.data.entity.Room;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class RoomDateProxy {

    private Room room;
    private Set<Date> dates = new HashSet<>();

    public RoomDateProxy(Room room, Set<Date> dates) {
        this.room = room;
        this.dates = dates;
    }

    @Override
    public String toString() {
        return "RoomDateProxy{" +
                "room=" + room +
                ", dates=" + dates +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoomDateProxy that = (RoomDateProxy) o;

        if (room != null ? !room.equals(that.room) : that.room != null) return false;
        return dates != null ? dates.equals(that.dates) : that.dates == null;
    }

    public boolean addDates(Date date){
        return dates.add(date);
    }

    public boolean addAllDates(Set<Date> dates){
        return this.dates.addAll(dates);
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Set<Date> getDates() {
        return dates;
    }

    public void setDates(Set<Date> dates) {
        this.dates = dates;
    }
}
