package com.theopus.knucaTelegram.data.service.impl;

import com.theopus.knucaTelegram.data.entity.Group;
import com.theopus.knucaTelegram.data.entity.Lesson;
import com.theopus.knucaTelegram.data.entity.Teacher;
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
    public List<Lesson> saveAll(Collection<Lesson> lessons) {
        int count = 0;
        for (Lesson l : lessons) {
            l.setGroups(groupService.saveAll(l.getGroups()));
            l.setTeachers(teacherService.saveAll(l.getTeachers()));
            l.setSubject(subjectService.saveOne(l.getSubject()));
            l.setRoomTimePeriod(roomService.saveRooms(l.getRoomTimePeriod()));
            lessonRepository.save(l);
            count++;
        }
        log.info("Successfully saved "  + count + " lessons.");
        this.flush();
        return (List<Lesson>) lessons;
    }

    @Override
    public Lesson saveOne(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    @Override
    public Lesson getById(long id) {
        return lessonRepository.getOne(id);
    }

    @Override
    public List<Lesson> getAll() {
        return lessonRepository.findAll();
    }

    @Override
    public long getCount() {
        return lessonRepository.count();
    }

    @Override
    public List<Lesson> getTodayByGroupName(String name) {
        return null;
    }

    @Override
    public List<Lesson> getExactDayByGroup(Date date, String groupName) {
        int dayOfWeek = DayOfWeek.dateToDayOfWeek(date).ordinal();
        List<Lesson> tmp = lessonRepository.findDayGroupName(dayOfWeek, groupName);

        List<Lesson> result = new ArrayList<>(Lesson.atDate(tmp, date));
        controlDuplicates(result);
        result.sort((o1, o2) -> o1.getOrder().ordinal() - o2.getOrder().ordinal());

        return result;
    }

    @Override
    public List<Lesson> getExactDayByGroup(Date date, long groupId) {
        int dayOfWeek = DayOfWeek.dateToDayOfWeek(date).ordinal();
        List<Lesson> tmp = lessonRepository.findDayGroupID(dayOfWeek, groupId);

        List<Lesson> result = new ArrayList<>(Lesson.atDate(tmp, date));
        controlDuplicates(result);
        result.sort((o1, o2) -> o1.getOrder().ordinal() - o2.getOrder().ordinal());
        return result;
    }

    @Override
    public List<Lesson> getExactDayByGroup(Date date, Group group) {
        return getExactDayByGroup(date, group.getId());
    }

    @Override
    public List<Lesson> getExactDayByTeacher(Date date, String teacherName) {
        long teacherID = teacherService.findByName(teacherName).getId();
        return getExactDayByTeacher(date, teacherID);
    }

    @Override
    public List<Lesson> getExactDayByTeacher(Date date, long teacherId) {
        int dayOfWeek = DayOfWeek.dateToDayOfWeek(date).ordinal();
        List<Lesson> tmp = lessonRepository.findDayTeacherID(dayOfWeek, teacherId);

        List<Lesson> result = new ArrayList<>(Lesson.atDate(tmp, date));
        controlDuplicates(result);
        result.sort((o1, o2) -> o1.getOrder().ordinal() - o2.getOrder().ordinal());
        return result;
    }

    @Override
    public List<Lesson> getExactDayByTeacher(Date date, Teacher teacher) {
        return getExactDayByTeacher(date, teacher.getId());
    }

    @Override
    public List<Lesson> getWeekByGroup(Group group) {
        return null;
    }

    @Override
    public List<Lesson> getWeekByGroup(Group group, int week) {
        return null;
    }

    @Override
    public List<Lesson> getWeekByGroup(long groupId, int week) {
        return null;
    }

    @Override
    public List<Lesson> getWeekByGroup(String groupName, int week) {
        return null;
    }

    @Override
    public List<Lesson> getWeekByTeacher(Teacher teacher) {
        return null;
    }

    @Override
    public List<Lesson> getWeekByTeacher(Teacher teacher, int week) {
        return null;
    }

    @Override
    public List<Lesson> getWeekByTeacher(long teacherId, int week) {
        return null;
    }

    @Override
    public List<Lesson> getWeekByTeacher(String teacherName, int week) {
        return null;
    }

    //TODO: tmp delete
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
                log.warn("--------Suspicious data------");
                result.forEach(lesson -> {
                    if (lesson.getOrder().equals(lessonOrder))
                        log.warn(lesson.toString());
                });
                log.warn("-----------------------------");
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
