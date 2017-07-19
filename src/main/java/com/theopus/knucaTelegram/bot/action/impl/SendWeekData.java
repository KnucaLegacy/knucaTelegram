package com.theopus.knucaTelegram.bot.action.impl;

import com.theopus.knucaTelegram.data.entity.Group;
import com.theopus.knucaTelegram.data.entity.Lesson;
import com.theopus.knucaTelegram.data.entity.Teacher;
import com.theopus.knucaTelegram.data.entity.enums.DayOfWeek;
import com.theopus.knucaTelegram.data.service.LessonService;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.util.*;

public class SendWeekData extends SendDayData {

    public SendWeekData(long chatId, LessonService service, Object targetEnt, Date date) {
        super(chatId, service, targetEnt, date);
    }

    @Override
    public Collection<SendMessage> buildMessage() {
        Map<DayOfWeek, List<Lesson>> lessonMap = null;
        if (targetEnt instanceof Group)
            lessonMap = service.getWeekByGroup(date, (Group) targetEnt,0);
        if (targetEnt instanceof Teacher)
            lessonMap = service.getWeekByTeacher(date, (Teacher) targetEnt, 0);
        Map<DayOfWeek, Date> dateMap = DayOfWeek.dateToDateMap(date);

        Set<SendMessage> result = new HashSet<>();
        Collection<String> strings = formater.weekLessonsToString(lessonMap, date, targetEnt);

        strings.forEach(s -> result.add(new SendMessage()
                .enableHtml(true)
                .setText(s)));
        return result;
    }
}
