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
            l.getRoomTimePeriod().forEach(roomTimePeriod -> roomTimePeriod.setLesson(l));
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

    private Map<DayOfWeek, List<Lesson>> sortByDayGroupWeekList(Date date, List<Lesson> lessons, int week){
        Set<Lesson> lessonList = new HashSet<>(lessons);
        Map<DayOfWeek,List<Lesson>> resultMap = new TreeMap<>();
        Map<DayOfWeek, Date> dateMap = DayOfWeek.dateToDateMap(
                week == 0 ?
                        date :
                        DayOfWeek.weekDateOffset(date, week));

        for (Map.Entry<DayOfWeek, Date> dayDate : dateMap.entrySet()) {
            resultMap.put(dayDate.getKey(), new ArrayList<>());
            for (Lesson lesson : lessonList) {
                if (lesson.getDayOfWeek().equals(dayDate.getKey()) && lesson.isAtDate(dayDate.getValue()))
                    resultMap.get(dayDate.getKey()).add(lesson);
            }
            resultMap.get(dayDate.getKey()).sort((o1, o2) -> o1.getOrder().ordinal() - o2.getOrder().ordinal());
        }
        return resultMap;
    }

    private List<Lesson> sortByDateList(Date date, List<Lesson> listLesson){
        List<Lesson> result = new ArrayList<>(Lesson.atDate(listLesson, date));
        controlDuplicates(result);
        result.sort((o1, o2) -> o1.getOrder().ordinal() - o2.getOrder().ordinal());
        return result;
    }

    @Override
    public List<Lesson> getExactDayByGroup(Date date, String groupName, int offset) {
        date = offset == 0 ? date : DayOfWeek.dayDateOffset(date, offset);
        int dayOfWeek = DayOfWeek.dateToDayOfWeek(date).ordinal();
        List<Lesson> result = lessonRepository.findDayGroupName(dayOfWeek, groupName);

        return sortByDateList(date, result);
    }

    @Override
    public List<Lesson> getExactDayByGroup(Date date, long groupId, int offset) {
        date = offset == 0 ? date : DayOfWeek.dayDateOffset(date, offset);
        int dayOfWeek = DayOfWeek.dateToDayOfWeek(date).ordinal();
        List<Lesson> result = lessonRepository.findDayGroupID(dayOfWeek, groupId);

        return sortByDateList(date, result);
    }

    @Override
    public List<Lesson> getExactDayByGroup(Date date, Group group, int offset) {
        return getExactDayByGroup(date, group.getId(), offset);
    }

    @Override
    public List<Lesson> getExactDayByTeacher(Date date, String teacherName, int offset) {
        long teacherID = teacherService.findByName(teacherName).getId();
        return getExactDayByTeacher(date, teacherID, offset);
    }

    @Override
    public List<Lesson> getExactDayByTeacher(Date date, long teacherId, int offset) {
        date = offset == 0 ? date : DayOfWeek.dayDateOffset(date, offset);
        int dayOfWeek = DayOfWeek.dateToDayOfWeek(date).ordinal();
        List<Lesson> result = lessonRepository.findDayTeacherID(dayOfWeek, teacherId);
        return sortByDateList(date, result);
    }

    @Override
    public List<Lesson> getExactDayByTeacher(Date date, Teacher teacher, int offset) {
        return getExactDayByTeacher(date, teacher.getId(), offset);
    }

    @Override
    public Map<DayOfWeek, List<Lesson>> getWeekByGroup(Date date, Group group, int week) {
        return getWeekByGroup(date, group.getId(), week);
    }

    @Override
    public Map<DayOfWeek, List<Lesson>> getWeekByGroup(Date date, long groupId, int week) {
        return sortByDayGroupWeekList(date, lessonRepository.getAllByGroupId(groupId), week);
    }

    @Override
    public Map<DayOfWeek, List<Lesson>> getWeekByGroup(Date date, String groupName, int week) {
        return sortByDayGroupWeekList(date, lessonRepository.getAllByGroupName(groupName), week);
    }

    @Override
    public Map<DayOfWeek, List<Lesson>> getWeekByTeacher(Date date, Teacher teacher, int week) {
        return getWeekByTeacher(date, teacher.getId(), week);
    }

    @Override
    public Map<DayOfWeek, List<Lesson>> getWeekByTeacher(Date date, long teacherId, int week) {
        return sortByDayGroupWeekList(date, lessonRepository.getAllByTeacherId(teacherId), week);
    }

    @Override
    public Map<DayOfWeek, List<Lesson>> getWeekByTeacher(Date date, String teacherName, int week) {
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
