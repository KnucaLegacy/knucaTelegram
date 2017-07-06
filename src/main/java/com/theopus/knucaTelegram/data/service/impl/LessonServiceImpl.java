package com.theopus.knucaTelegram.data.service.impl;

import com.theopus.knucaTelegram.data.entity.Lesson;
import com.theopus.knucaTelegram.data.entity.enums.DayOfWeek;
import com.theopus.knucaTelegram.data.repository.LessonRepository;
import com.theopus.knucaTelegram.data.service.GroupService;
import com.theopus.knucaTelegram.data.service.LessonService;
import com.theopus.knucaTelegram.data.service.SubjectService;
import com.theopus.knucaTelegram.data.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

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
    public List<Lesson> getTodayByGroupName(String name) {
        return null;
    }

    @Override
    public List<Lesson> getByExactDayByGroupName(Date date, String name) {
        List<Lesson> result = new ArrayList<>();

        int dayOfweek = DayOfWeek.dateToDayOfWeek(date).ordinal();

        List<Lesson> tmp = lessonRepository.findDayGroupName(dayOfweek, name);
        System.out.println(tmp);

        tmp.forEach(lesson -> {
            if (lesson.isAtDate(date))
                result.add(lesson);
        });
        System.out.println(result);
        result.sort((o1, o2) -> o1.getOrder().ordinal() - o2.getOrder().ordinal());
        return result;
    }

    @Override
    public void flush() {
        groupService.flush();
        teacherService.flush();
        subjectService.flush();
        roomService.flush();
    }
}
