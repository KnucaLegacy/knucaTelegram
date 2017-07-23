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

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM");
    @Override
    public String getCallBackQuery() {
        StringBuilder builder = new StringBuilder();
        if (targetEnt instanceof Group) {
            Group group = (Group) targetEnt;
            builder.append(group.getName()).append(" ");
        }
        if (targetEnt instanceof Teacher) {
            Teacher teacher = (Teacher) targetEnt;
            builder.append(teacher.getName()).append(" ");
        }
        if (date!=null)
        builder.append(simpleDateFormat.format(date)).append(" ");
        return builder.toString();
    }

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
            lessonList = service.getExactDayByGroup(date, (Group) targetEnt);
        if (targetEnt instanceof Teacher)
            lessonList = service.getExactDayByTeacher(date, (Teacher) targetEnt);
        SendMessage sendMessage = new SendMessage();
        sendMessage
                .setText(formater.dayLessonsToString(lessonList,targetEnt,date))
                .enableHtml(true);
        resultSet.add(sendMessage);
        resultSet.add(getKeyBoard());
        return resultSet;
    }

    protected SendMessage getKeyBoard(){
       return new SendInlineKeyboard(chatId, targetEnt).buildMessage().stream().findFirst().orElse(null);
    }

    public Object getTargetEnt() {
        return targetEnt;
    }

    void setTargetEnt(Object targetEnt) {
        this.targetEnt = targetEnt;
    }
}
