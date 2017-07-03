package com.theopus.knucaTelegram.service.massupload.impl;

import com.theopus.knucaTelegram.entity.Lesson;
import com.theopus.knucaTelegram.repository.LessonRepository;
import com.theopus.knucaTelegram.repository.TeacherRepository;
import com.theopus.knucaTelegram.service.massupload.MassUploadGroupService;
import com.theopus.knucaTelegram.service.massupload.MassUploadLessonService;
import com.theopus.knucaTelegram.service.massupload.MassUploadTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by irina on 03.07.17.
 */
@Service
public class MassUploadLessonServiceImpl implements MassUploadLessonService {

    @Resource
    private MassUploadGroupService groupService;
    @Resource
    private MassUploadTeacherService teacherService;
    @Resource
    private MUSubjectService subjectService;
    @Resource
    private MURoomService roomService;

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

        return lessons;
    }
}
