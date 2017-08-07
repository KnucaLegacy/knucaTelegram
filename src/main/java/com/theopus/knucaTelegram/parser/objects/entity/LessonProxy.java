package com.theopus.knucaTelegram.parser.objects.entity;

import com.theopus.knucaTelegram.entity.Group;
import com.theopus.knucaTelegram.entity.Room;
import com.theopus.knucaTelegram.entity.Subject;
import com.theopus.knucaTelegram.entity.Teacher;
import com.theopus.knucaTelegram.entity.enums.LessonType;

import java.util.HashSet;
import java.util.Set;

public class LessonProxy {

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
                ", subject=" + subject +
                ", lessonType=" + lessonType +
                ", teachers=" + teachers +
                ", groups=" + groups +
                ", ownerGroup=" + ownerGroup +
                ", roomDateProxies=" + roomDateProxies +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LessonProxy that = (LessonProxy) o;

        if (subject != null ? !subject.equals(that.subject) : that.subject != null) return false;
        if (lessonType != that.lessonType) return false;
        if (!teachers.containsAll(that.teachers)) return false;
        if (!that.teachers.containsAll(teachers)) return false;
        if (!groups.containsAll(that.teachers)) return false;
        if (!that.groups.containsAll(groups)) return false;
        if (!roomDateProxies.containsAll(that.roomDateProxies)) return false;
        if (!that.roomDateProxies.containsAll(roomDateProxies)) return false;
        if (!this.ownerGroup.equals(that.ownerGroup)) return false;
        return true;
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
