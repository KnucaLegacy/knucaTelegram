package com.theopus.knucaTelegram.bot.command;

import com.theopus.knucaTelegram.data.entity.Group;
import com.theopus.knucaTelegram.data.entity.Lesson;
import com.theopus.knucaTelegram.data.entity.enums.DayOfWeek;
import com.theopus.knucaTelegram.data.entity.enums.LessonOrder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

@Component
public class TelegramMessageFormater {

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

    public String dateHeader(DayOfWeek dayOfWeek, Date date){
        StringBuilder headerBuilder = new StringBuilder();
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        headerBuilder.append(DayOfWeek.toReadable(dayOfWeek)).append(" - ").append(format.format(date)).append("\n\n");
        return headerBuilder.toString();
    }

    public String groupHeader(Group group){
        return new StringBuilder().append("<b>").append(group.getName()).append("</b>").append("\n").toString();
    }

    private String noLessonsMessage(){
        return "На текущую дату нет пар!";
    }
}
