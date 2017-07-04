package com.theopus.knucaTelegram.data.service.impl;

import com.theopus.knucaTelegram.data.entity.Lesson;
import com.theopus.knucaTelegram.data.repository.LessonRepository;
import com.theopus.knucaTelegram.data.service.GroupService;
import com.theopus.knucaTelegram.data.service.LessonService;
import com.theopus.knucaTelegram.data.service.SubjectService;
import com.theopus.knucaTelegram.data.service.TeacherService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by irina on 03.07.17.
 */
@Service
public class LessonServiceImpl implements LessonService {

    @Resource
    private GroupService groupService;
    @Resource
    private TeacherService teacherService;
    @Resource
    private SubjectService subjectService;
    @Resource
    private RoomServiceImpl roomService;

    @Resource
    private LessonRepository lessonRepository;


    @Override
    public List<Lesson> saveAll(List<Lesson> lessons) {
        for (Lesson l : lessons) {
            l.setGroups(groupService.saveAll(l.getGroups()));
            l.setTeachers(teacherService.saveAll(l.getTeachers()));
            l.setSubject(subjectService.saveOne(l.getSubject()));
            l.setRoomTimePeriod(roomService.saveRooms(l.getRoomTimePeriod()));
            lessonRepository.save(l);
        }
        this.flush();
        return lessons;
    }

    @Override
    public List<Lesson> getAll() {
        return lessonRepository.findAll();
    }


    @Override
    public List<Lesson> getByWeekDayGroupName(int day, String name) {
        return lessonRepository.findDayGroupName(day, name);
    }

    @Override
    public void flush() {
        groupService.flush();
        teacherService.flush();
        subjectService.flush();
        roomService.flush();
    }
}
