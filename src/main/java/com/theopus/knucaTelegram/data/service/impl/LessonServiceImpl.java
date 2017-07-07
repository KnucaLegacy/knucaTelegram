package com.theopus.knucaTelegram.data.service.impl;

import com.theopus.knucaTelegram.data.entity.Lesson;
import com.theopus.knucaTelegram.data.entity.enums.DayOfWeek;
import com.theopus.knucaTelegram.data.entity.enums.LessonOrder;
import com.theopus.knucaTelegram.data.repository.LessonRepository;
import com.theopus.knucaTelegram.data.service.GroupService;
import com.theopus.knucaTelegram.data.service.LessonService;
import com.theopus.knucaTelegram.data.service.SubjectService;
import com.theopus.knucaTelegram.data.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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

        tmp.forEach(lesson -> {
            if (lesson.isAtDate(date))
                result.add(lesson);
        });

        controlDuplicates(result);

        result.sort((o1, o2) -> o1.getOrder().ordinal() - o2.getOrder().ordinal());
        return result;
    }

    private Set<Lesson> controlDuplicates(List<Lesson> result){
        Map<LessonOrder, Integer> integerMap = new HashMap<>();
        result.forEach(lesson -> {
            if (integerMap.containsKey(lesson.getOrder()))
                integerMap.put(lesson.getOrder(), integerMap.get(lesson.getOrder()) + 1);
            else
                integerMap.put(lesson.getOrder(), 1);
        });
        integerMap.forEach((lessonOrder, integer) -> {
            if (integer >= 2){
                log.warn("--------------suspicious-----------");
                result.forEach(lesson -> {
                    if (lesson.getOrder().equals(lessonOrder))
                        log.warn(lesson.toString());
                });
                log.warn("--------------suspicious-----------");
            }
        });
        return null;
    }

    @Override
    public void flush() {
        groupService.flush();
        teacherService.flush();
        subjectService.flush();
        roomService.flush();
    }
}
