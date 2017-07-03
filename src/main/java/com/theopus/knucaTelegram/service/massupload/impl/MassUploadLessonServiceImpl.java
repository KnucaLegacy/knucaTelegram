package com.theopus.knucaTelegram.service.massupload.impl;

import com.theopus.knucaTelegram.entity.Lesson;
import com.theopus.knucaTelegram.repository.LessonRepository;
import com.theopus.knucaTelegram.service.massupload.GroupService;
import com.theopus.knucaTelegram.service.massupload.MassUploadLessonService;
import com.theopus.knucaTelegram.service.massupload.SubjectService;
import com.theopus.knucaTelegram.service.massupload.TeacherService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by irina on 03.07.17.
 */
@Service
public class MassUploadLessonServiceImpl implements MassUploadLessonService {

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
    public void flush() {
        groupService.flush();
        teacherService.flush();
        subjectService.flush();
        roomService.flush();
    }
}
