package com.theopus.knucaTelegram.service;

import com.theopus.knucaTelegram.entity.Circumstance;
import com.theopus.knucaTelegram.entity.Lesson;
import com.theopus.knucaTelegram.entity.Room;
import com.theopus.knucaTelegram.entity.SimpleLesson;
import com.theopus.knucaTelegram.entity.enums.DayOfWeek;
import com.theopus.knucaTelegram.entity.enums.LessonOrder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by irina on 02.08.17.
 */
@Service
public class LessonSimplifier {


    public List<SimpleLesson> simplify(Collection<Lesson> lessons, Date date){
       List<SimpleLesson> result = new ArrayList<>();
       lessons.forEach(lesson -> {
           result.addAll(simplify(lesson,date));
       });
       result.sort(Comparator.comparingInt(o -> o.getOrder().ordinal()));
       return result;
    }

    public List<SimpleLesson> simplify(Lesson lesson, Date date){
        Map<LessonOrder, Set<Room>> orderRoomMap = new HashMap<>();
        for (Circumstance circumstance : lesson.getCircumstances()) {
            for (Date date1 : circumstance.getDates()) {
                if (date.equals(date1)){
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
        List<SimpleLesson> result = new ArrayList<>();
        result.sort(Comparator.comparingInt(o -> o.getOrder().ordinal()));
        orderRoomMap.forEach((lessonOrder, rooms) -> {
            result.add(new SimpleLesson(
                    lesson.getId(),
                    DayOfWeek.dateToDayOfWeek(date),
                    lessonOrder,
                    lesson.getLessonType(),
                    lesson.getSubject(),
                    lesson.getGroups(),
                    lesson.getTeachers(),
                    rooms,
                    date));
        });
        return result;
    }

    public Map<Date,List<SimpleLesson>> simplify(Map<Date, List<Lesson>> lessonsMap){
        Map<Date,List<SimpleLesson>> result = new LinkedHashMap<>();
        lessonsMap.forEach((date, lessons) -> {
            result.put(date, simplify(lessons,date));
        });
        return result;
    }
}
