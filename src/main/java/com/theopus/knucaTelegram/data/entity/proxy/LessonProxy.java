package com.theopus.knucaTelegram.data.entity.proxy;

import com.theopus.knucaTelegram.data.entity.Group;
import com.theopus.knucaTelegram.data.entity.Room;
import com.theopus.knucaTelegram.data.entity.Subject;
import com.theopus.knucaTelegram.data.entity.Teacher;
import com.theopus.knucaTelegram.data.entity.enums.LessonOrder;
import com.theopus.knucaTelegram.data.entity.enums.LessonType;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class LessonProxy {

    private long id;
    private LessonOrder order;
    private Subject subject;
    private LessonType lessonType;
    private Set<Teacher> teachers = new HashSet<>();
    private Set<Group> groups = new HashSet<>();
    private Group ownerGroup;
    private Set<RoomDateProxy> roomDateProxies = new HashSet<>();

    public LessonProxy() {
    }

    @Override
    public String toString() {
        return "LessonProxy{" +
                "id=" + id +
                ", order=" + order +
                ", subject=" + subject +
                ", lessonType=" + lessonType +
                ", teachers=" + teachers +
                ", groups=" + groups +
                ", ownerGroup=" + ownerGroup +
                ", roomDateProxies=" + roomDateProxies +
                '}';
    }

    public Set<Room> getRooms(){
        Set<Room> result = new HashSet<>();
        roomDateProxies.forEach(roomDateProxy -> result.add(roomDateProxy.getRoom()));
        return result;
    }

    public boolean addGroup(Group group){
        return groups.add(group);
    }

    public boolean addAllGroup(Set<Group> groups){
        return this.groups.addAll(groups);
    }

    public boolean addTeacher(Teacher teacher){
        return teachers.add(teacher);
    }

    public boolean addAllTeacher(Set<Teacher> teachers){
        return this.teachers.addAll(teachers);
    }

    public boolean addRoomDateProxy(RoomDateProxy roomDateProxy){
        return roomDateProxies.add(roomDateProxy);
    }

    public boolean addAllRoomDateProxy(Set<RoomDateProxy> roomDateProxies){
        return this.roomDateProxies.addAll(roomDateProxies);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LessonOrder getOrder() {
        return order;
    }

    public void setOrder(LessonOrder order) {
        this.order = order;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public LessonType getLessonType() {
        return lessonType;
    }

    public void setLessonType(LessonType lessonType) {
        this.lessonType = lessonType;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public Group getOwnerGroup() {
        return ownerGroup;
    }

    public void setOwnerGroup(Group ownerGroup) {
        this.ownerGroup = ownerGroup;
    }

    public Set<RoomDateProxy> getRoomDateProxies() {
        return roomDateProxies;
    }

    public void setRoomDateProxies(Set<RoomDateProxy> roomDateProxies) {
        this.roomDateProxies = roomDateProxies;
    }
}
