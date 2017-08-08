package com.theopus.knucaTelegram.entity.schedule;

import com.fasterxml.jackson.annotation.JsonView;
import com.theopus.knucaTelegram.controller.ajax.View;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Room {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name= "increment", strategy= "increment")
    @Column(name = "id", length = 6, nullable = false)
    @JsonView(View.Summary.class)
    private long id;

    @Column
    @JsonView(View.Summary.class)
    private String name;
//
//    @OneToMany(mappedBy = "room")
//    @JsonIgnore
//    private Set<RoomTimePeriod> roomTimePeriodSet;

    public Room() {
    }

    public Room(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        return name != null ? name.equals(room.name) : room.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Room{" +
                "name='" + name + '\'' +
                '}';
    }
}
