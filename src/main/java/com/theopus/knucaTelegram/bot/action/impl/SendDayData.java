package com.theopus.knucaTelegram.bot.action.impl;

import com.theopus.knucaTelegram.bot.action.SingleDirSendMessageAction;
import com.theopus.knucaTelegram.data.entity.Group;
import com.theopus.knucaTelegram.data.entity.Lesson;
import com.theopus.knucaTelegram.data.entity.Teacher;
import com.theopus.knucaTelegram.data.service.LessonService;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.util.*;

public class SendDayData extends SingleDirSendMessageAction{

    protected LessonService service;
    protected Object targetEnt;
    protected Date date;

    public SendDayData(long chatId, LessonService service, Object targetEnt, Date date) {
        super(chatId);
        this.service = service;
        this.targetEnt = targetEnt;
        this.date = date;
    }

    @Override
    public Collection<SendMessage> buildMessage() {
        Set<SendMessage> resultSet = new LinkedHashSet<>();
        List<Lesson> lessonList = null;
        if (targetEnt instanceof Group)
            lessonList = service.getExactDayByGroup(date, (Group) targetEnt, 0);
        if (targetEnt instanceof Teacher)
            lessonList = service.getExactDayByTeacher(date, (Teacher) targetEnt, 0);
        SendMessage sendMessage = new SendMessage();
        sendMessage
                .setText(formater.dayLessonsToString(lessonList,targetEnt,date))
                .enableHtml(true);
        resultSet.add(sendMessage);
        return resultSet;
    }
}
