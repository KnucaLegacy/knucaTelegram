package com.theopus.knucaTelegram.bot.action.implsenddata;

import com.theopus.knucaTelegram.entity.schedule.Group;
import com.theopus.knucaTelegram.entity.schedule.SimpleLesson;
import com.theopus.knucaTelegram.entity.schedule.Teacher;
import com.theopus.knucaTelegram.service.data.SimpleLessonService;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.util.*;

public class SendFromToData extends SendDayData {

    private Date to;

    @Override
    public String getCallBackQuery() {
        StringBuilder builder = new StringBuilder(super.getCallBackQuery());
        builder.append(simpleDateFormat.format(to)).append(" ");
        return builder.toString();
    }

    public SendFromToData(long chatId, SimpleLessonService service, Object targetEnt, Date date, Date to) {
        super(chatId, service, targetEnt, date);
        this.to = to;
    }

    @Override
    public Collection<SendMessage> buildMessage() {
        Set<SendMessage> sendMessages = new LinkedHashSet<>();
        Map<Date, List<SimpleLesson>> lessonList = null;
        if (targetEnt instanceof Group){
            Group group = (Group) targetEnt;
            lessonList = service.getByGroup(date, to, group);
        }
        if (targetEnt instanceof Teacher){
            Teacher teacher = (Teacher) targetEnt;
            lessonList = service.getByTeacher(date, to, teacher);
        }
        if (lessonList != null) {
            lessonList.forEach((dat, lessonList1) -> {
                sendMessages
                        .add(new SendMessage()
                                .enableHtml(true)
                                .setText(formater.toDayMessage(lessonList1, targetEnt,dat)));
            });
        }
        sendMessages.add(getKeyBoard());
        return sendMessages;
    }
}
