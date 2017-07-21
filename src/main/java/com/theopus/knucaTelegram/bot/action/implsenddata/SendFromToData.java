package com.theopus.knucaTelegram.bot.action.implsenddata;

import com.theopus.knucaTelegram.data.entity.Group;
import com.theopus.knucaTelegram.data.entity.Lesson;
import com.theopus.knucaTelegram.data.entity.Teacher;
import com.theopus.knucaTelegram.data.service.LessonService;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.util.*;

public class SendFromToData extends SendDayData {

    private Date to;

    public SendFromToData(long chatId, LessonService service, Object targetEnt, Date date, Date to, int offset) {
        super(chatId, service, targetEnt, date, offset);
        this.to = to;
    }

    @Override
    public Collection<SendMessage> buildMessage() {
        Set<SendMessage> sendMessages = new LinkedHashSet<>();
        Map<Date, List<Lesson>> lessonList = null;
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
                                .setText(formater.dayLessonsToString(lessonList1, targetEnt, dat)));
            });
        }
        return sendMessages;
    }
}
