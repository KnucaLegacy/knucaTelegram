package com.theopus.knucaTelegram.bot.action.implsenddata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.theopus.knucaTelegram.bot.action.CallBackable;
import com.theopus.knucaTelegram.bot.action.SingleDirSendMessageAction;
import com.theopus.knucaTelegram.data.entity.Group;
import com.theopus.knucaTelegram.data.entity.Lesson;
import com.theopus.knucaTelegram.data.entity.Teacher;
import com.theopus.knucaTelegram.data.service.LessonService;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.text.SimpleDateFormat;
import java.util.*;

public class SendDayData extends SingleDirSendMessageAction implements CallBackable{

    @JsonIgnore
    protected LessonService service;

    protected Object targetEnt;
    protected Date date;
    protected int offset;


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

    public SendDayData(long chatId, LessonService service, Object targetEnt, Date date, int offset) {
        super(chatId);
        this.service = service;
        this.targetEnt = targetEnt;
        this.date = date;
        this.offset = offset;
    }

    @Override
    public Collection<SendMessage> buildMessage() {
        Set<SendMessage> resultSet = new LinkedHashSet<>();
        List<Lesson> lessonList = null;
        if (targetEnt instanceof Group)
            lessonList = service.getExactDayByGroup(date, (Group) targetEnt, offset);
        if (targetEnt instanceof Teacher)
            lessonList = service.getExactDayByTeacher(date, (Teacher) targetEnt, offset);
        SendMessage sendMessage = new SendMessage();
        sendMessage
                .setText(formater.dayLessonsToString(lessonList,targetEnt,date))
                .enableHtml(true);
        resultSet.add(sendMessage);
        return resultSet;
    }

    public Object getTargetEnt() {
        return targetEnt;
    }

    public void setTargetEnt(Object targetEnt) {
        this.targetEnt = targetEnt;
    }
}
