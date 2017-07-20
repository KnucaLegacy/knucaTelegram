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

    public SendWeekData(long chatId, LessonService service, Object targetEnt, Date date, int offset) {
        super(chatId, service, targetEnt, date, offset);
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
                ", offset=" + offset +
                '}';
    }

    @Override
    public String getCallBackQuery() {
        return toString();
    }

    @Override
    public Collection<SendMessage> buildMessage() {
        Map<DayOfWeek, List<Lesson>> lessonMap = null;
        if (targetEnt instanceof Group)
            lessonMap = service.getWeekByGroup(date, (Group) targetEnt,offset);
        if (targetEnt instanceof Teacher)
            lessonMap = service.getWeekByTeacher(date, (Teacher) targetEnt, offset);
        Map<DayOfWeek, Date> dateMap = DayOfWeek.dateToDateMap(date);

        Set<SendMessage> result = new HashSet<>();
        Collection<String> strings = formater.weekLessonsToString(lessonMap, date, targetEnt);

        strings.forEach(s -> result.add(new SendMessage()
                .enableHtml(true)
                .setText(s)));
        return result;
    }
}
