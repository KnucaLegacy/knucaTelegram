package com.theopus.knucaTelegram.service;

import com.theopus.knucaTelegram.entity.schedule.*;
import com.theopus.knucaTelegram.entity.schedule.enums.DayOfWeek;
import com.theopus.knucaTelegram.entity.schedule.enums.LessonOrder;
import com.theopus.knucaTelegram.entity.schedule.enums.LessonType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import stubs.*;

import javax.annotation.Resource;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {LessonSimplifier.class})
public class LessonSimplifierTest {

    @Resource
    private LessonSimplifier lessonSimplifier;

    private List<Lesson> lessonList = new ArrayList<>();
    private Map<Date, List<Lesson>> map = new HashMap<>();

    private Date gloabaldate = DayOfWeek.dateToRawDate(new Date());

    @Before
    public void setUp() throws Exception {
        lessonList = LessonStubs.makeList(gloabaldate);
        map = LessonStubs.makeMap(gloabaldate);
    }

    @Test
    public void simplifyOneLesson() throws Exception {
        final Lesson lesson = new Lesson();
        lesson.setGroups(new HashSet<>(GroupStubs.getRand(3)));
        lesson.setSubject(SubjectStubs.get3());
        lesson.setTeachers(new HashSet<>(TeacherStubs.getRand(1)));
        lesson.setCircumstances(CircumstanceStubs.getCircumstances(
                gloabaldate,
                2,
                3,
                2,
                false));
        lesson.setLessonType(LessonType.LAB);

        List<SimpleLesson> simplify = lessonSimplifier.simplify(lesson, DayOfWeek.dateToRawDate(gloabaldate));
        final boolean[] isAtDate = {false};
        simplify.forEach(simpleLesson -> {
            Assert.assertEquals(lesson.getId(), simpleLesson.getFullid());
            Assert.assertEquals(lesson.getSubject(), simpleLesson.getSubject());
            Assert.assertEquals(lesson.getGroups(), simpleLesson.getGroups());
            Assert.assertEquals(lesson.getTeachers(), simpleLesson.getTeachers());
            Assert.assertEquals(lesson.getLessonType(), simpleLesson.getLessonType());
            lesson.getCircumstances().forEach(circumstance -> {
                boolean isAtCurrDate = false;
                isAtCurrDate = circumstance.getDates().contains(simpleLesson.getDate());
                if (isAtCurrDate){
                    Assert.assertTrue(simpleLesson.getRooms().contains(circumstance.getRoom()));
                    isAtDate[0] = true;
                }
            });
        });
        Assert.assertTrue(isAtDate[0]);
    }

    @Test
    public void simlifyOneLesson_multipleRooms() throws Exception {
        final Lesson lesson = new Lesson();
        lesson.setGroups(new HashSet<>(GroupStubs.getRand(3)));
        lesson.setSubject(SubjectStubs.get3());
        lesson.setTeachers(new HashSet<>(TeacherStubs.getRand(1)));
        lesson.setCircumstances(CircumstanceStubs.getCircumstances(
                gloabaldate,
                2,
                3,
                4,
                true));
        lesson.setLessonType(LessonType.LAB);

        Map<LessonOrder, Set<Room>> orderRoomMap = new HashMap<>();
        for (Circumstance circumstance : lesson.getCircumstances()) {
            for (Date date1 : circumstance.getDates()) {
                if (gloabaldate.equals(date1)){
                    Set<Room> rooms = orderRoomMap.get(circumstance.getOrder());
                    if (rooms != null)
                        rooms.add(circumstance.getRoom());
                    else {
                        rooms = new HashSet<>();
                        rooms.add(circumstance.getRoom());
                        orderRoomMap.put(circumstance.getOrder(), rooms);
                    }
                    break;
                }

            }

        }
        boolean[] flag = new boolean[]{false};
        List<SimpleLesson> simplify = lessonSimplifier.simplify(lesson, DayOfWeek.dateToRawDate(gloabaldate));
        orderRoomMap.forEach((order, rooms) -> {
            simplify.forEach(simpleLesson -> {
                if (simpleLesson.getOrder().equals(order)){
                    if (simpleLesson.getRooms().containsAll(rooms)){
                        flag[0] = true;
                    }
                }
            });
        });
        assertTrue(flag[0]);
    }

    @Test
    public void simplifyOneDay() throws Exception {
        List<SimpleLesson> simplify = lessonSimplifier.simplify(lessonList, gloabaldate);
        simplify.forEach(simpleLesson -> {
            assertTrue(simpleLesson.getDate().equals(gloabaldate));
        });
    }

    @Test
    public void simplifyMap() throws Exception {
        Map<Date, List<SimpleLesson>> simplify = lessonSimplifier.simplify(map);
        simplify.forEach((date, simpleLessons) -> {
            simpleLessons.forEach(simpleLesson -> {
                assertTrue(simpleLesson.getDate().equals(date));
            });
        });
    }

}