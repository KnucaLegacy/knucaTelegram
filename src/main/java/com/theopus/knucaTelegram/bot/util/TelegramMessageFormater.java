package com.theopus.knucaTelegram.bot.util;

import com.theopus.knucaTelegram.data.entity.Group;
import com.theopus.knucaTelegram.data.entity.Lesson;
import com.theopus.knucaTelegram.data.entity.Teacher;
import com.theopus.knucaTelegram.data.entity.enums.DayOfWeek;
import com.theopus.knucaTelegram.data.entity.enums.LessonOrder;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class TelegramMessageFormater {

    public Collection<String> weekLessonsToString(Map<DayOfWeek, List<Lesson>> lessonMap, Date weekDate, Object o){
        Map<DayOfWeek, Date> dateMap = DayOfWeek.dateToDateMap(weekDate);

        List<String> messages = new ArrayList<>();

        for (Map.Entry<DayOfWeek, Date> dateMapPair: dateMap.entrySet()){
            if (!lessonMap.get(dateMapPair.getKey()).isEmpty()) {
                Date pairDate = dateMapPair.getValue();
                dayLessonsToString(lessonMap.get(dateMapPair.getKey()),o,pairDate);
                messages.add(dayLessonsToString(lessonMap.get(dateMapPair.getKey()),o,pairDate));
            }
        }
        return messages;
    }

    public String dayLessonsToString(Collection<Lesson> lessons, Object o, Date date){

        StringBuilder message = new StringBuilder();
        if (o instanceof Group)
            message.append(groupHeader((Group) o));
        if (o instanceof Teacher)
            message.append(teacherHeader((Teacher) o));
        message.append(dateHeader(date));
        if (lessons.isEmpty()){
            message.append(noLessonsMessage());
            return message.toString();
        }
        message.append(lessonsToString(lessons,date));
        return message.toString();
    }

    public String lessonsToString(Collection<Lesson> lessons, Date date){
        StringBuilder lessonListBuilder = new StringBuilder();
        for (Lesson lesson: lessons) {
            lessonListBuilder.append(lessonToString(lesson, date)).append("\n");
        }
        if (lessons.size() == 0 ){
            lessonListBuilder.append(noLessonsMessage());
        }
        return lessonListBuilder.toString();
    }

    private String lessonToString(Lesson l, Date date){
        StringBuilder lessonBuilder = new StringBuilder();
        lessonBuilder.append("<b>").append("| ").append(LessonOrder.toTimeString(l.getOrder())).append(" | ").append("</b>");
        lessonBuilder.append(l.getSubject().getName()).append(" | ").append("\n");
        lessonBuilder.append(" |");
        l.getRoomByDate(date).forEach(r -> lessonBuilder.append(replaceDiamonds(r.getName())).append(" | "));
        lessonBuilder.append(l.getLessonType().toString()).append(" |");
        lessonBuilder.append("\n |");
        l.getTeachers().forEach(t -> lessonBuilder.append(t.getName()).append(" | "));
        lessonBuilder.append("\n |");
        l.getGroups().forEach(group -> lessonBuilder.append(group.getName()).append(" | "));
        lessonBuilder.append("\n ");
        return lessonBuilder.toString();
    }

    private String replaceDiamonds(String string){
        return  StringUtils.replaceChars(string, "<>", "[]");
    }

    public String dateHeader(Date date){
        StringBuilder headerBuilder = new StringBuilder();
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        headerBuilder
                .append(DayOfWeek.toReadable(DayOfWeek.dateToDayOfWeek(date)))
                .append(" - ")
                .append(format.format(date))
                .append("\n\n");
        return headerBuilder.toString();
    }

    public String groupHeader(Group group){
        return "<b>" + group.getName() + "</b>" + "\n";
    }

    public String teacherHeader(Teacher teacher){
        return "<b>" + teacher.getName() + "</b>" + "\n";
    }

    private String noLessonsMessage(){
        return "На текущую дату нет пар!";
    }
}
