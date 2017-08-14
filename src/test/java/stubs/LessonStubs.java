package stubs;

import com.theopus.knucaTelegram.entity.schedule.Lesson;
import com.theopus.knucaTelegram.entity.schedule.enums.DayOfWeek;
import com.theopus.knucaTelegram.entity.schedule.enums.LessonType;

import java.util.*;

public class LessonStubs {

    public static List<Lesson> makeList(Date date) throws Exception {
        final Lesson lesson1 = new Lesson();
        lesson1.setGroups(new HashSet<>(GroupStubs.getRand(3)));
        lesson1.setSubject(SubjectStubs.get3());
        lesson1.setTeachers(new HashSet<>(TeacherStubs.getRand(1)));
        lesson1.setCircumstances(CircumstanceStubs.getCircumstances(
                date,
                3,
                3,
                2,
                false));
        lesson1.setLessonType(LessonType.LAB);
        final Lesson lesson2 = new Lesson();
        lesson2.setGroups(new HashSet<>(GroupStubs.getRand(3)));
        lesson2.setSubject(SubjectStubs.get3());
        lesson2.setTeachers(new HashSet<>(TeacherStubs.getRand(1)));
        lesson2.setCircumstances(CircumstanceStubs.getCircumstances(
                date,
                2,
                3,
                2,
                false));
        lesson2.setLessonType(LessonType.LAB);
        final Lesson lesson3 = new Lesson();
        lesson3.setGroups(new HashSet<>(GroupStubs.getRand(3)));
        lesson3.setSubject(SubjectStubs.get3());
        lesson3.setTeachers(new HashSet<>(TeacherStubs.getRand(1)));
        lesson3.setCircumstances(CircumstanceStubs.getCircumstances(
                date,
                1,
                3,
                2,
                false));
        lesson3.setLessonType(LessonType.LAB);
        final Lesson lesson4 = new Lesson();
        lesson4.setGroups(new HashSet<>(GroupStubs.getRand(3)));
        lesson4.setSubject(SubjectStubs.get3());
        lesson4.setTeachers(new HashSet<>(TeacherStubs.getRand(1)));
        lesson4.setCircumstances(CircumstanceStubs.getCircumstances(
                date,
                0,
                3,
                2,
                false));
        lesson4.setLessonType(LessonType.LAB);
        List<Lesson> lessons = new ArrayList<>();
        lessons.add(lesson1);
        lessons.add(lesson2);
        lessons.add(lesson3);
        lessons.add(lesson4);
        return lessons;
    }

    public static Map<Date, List<Lesson>> makeMap(Date date) throws Exception {
        Map<Date, List<Lesson>> map = new HashMap<>();
        for (int i = 0; i < 6; i++) {
            Date tmp = DayOfWeek.dayDateOffset(DayOfWeek.dateToRawDate(date),i);
            map.put(tmp, makeList(tmp));
        }
        return map;
    }

}
