package com.theopus.knucaTelegram.bot.action.implsenddata;

import com.theopus.knucaTelegram.data.entity.Group;
import com.theopus.knucaTelegram.data.entity.Lesson;
import com.theopus.knucaTelegram.data.entity.Teacher;
import com.theopus.knucaTelegram.data.entity.enums.DayOfWeek;
import com.theopus.knucaTelegram.data.service.LessonService;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.text.SimpleDateFormat;
import java.util.*;

public class SendWeekData extends SendDayData {

    public SendWeekData(long chatId, LessonService service, Object targetEnt, Date date) {
        super(chatId, service, targetEnt, date);
    }

    @Override
    public String toString() {
        String date = null;
        if (this.date != null) {
            date = new SimpleDateFormat("dd-MM-yyyy").format(this.date);
        }
        return "SendDayData{" +
                "targetEnt=" + targetEnt +
                (this.date != null ? (", date=" + date) : "") +
                '}';
    }

    @Override
    public String getCallBackQuery() {
        StringBuilder builder = new StringBuilder(super.getCallBackQuery());
        builder.append("week").append(" ");
        return builder.toString();
    }

    @Override
    public Collection<SendMessage> buildMessage() {
        Map<Date, List<Lesson>> lessonMap = null;
        if (targetEnt instanceof Group)
            lessonMap = service.getWeekByGroup(date, (Group) targetEnt);
        if (targetEnt instanceof Teacher)
            lessonMap = service.getWeekByTeacher(date, (Teacher) targetEnt);
        Map<DayOfWeek, Date> dateMap = DayOfWeek.dateToDateMap(date);

        Set<SendMessage> result = new LinkedHashSet<>();

        lessonMap.forEach((date1, lessonList) -> {
            result.add(new SendMessage()
                .enableHtml(true)
                .setText(formater.dayLessonsToString(lessonList,targetEnt,date1)));
        });
        result.add(getKeyBoard());
        return result;
    }
}
