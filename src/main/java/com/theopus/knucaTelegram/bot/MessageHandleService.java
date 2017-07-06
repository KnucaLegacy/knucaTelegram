package com.theopus.knucaTelegram.bot;


import com.theopus.knucaTelegram.data.entity.Group;
import com.theopus.knucaTelegram.data.entity.Lesson;
import com.theopus.knucaTelegram.data.entity.enums.DayOfWeek;
import com.theopus.knucaTelegram.data.entity.enums.LessonOrder;
import com.theopus.knucaTelegram.data.service.GroupService;
import com.theopus.knucaTelegram.data.service.LessonService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class MessageHandleService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private GroupService groupService;
    @Resource
    private LessonService lessonService;

    private GregorianCalendar mockToday = new GregorianCalendar(2017, 4, 15);

    public MessageHandleService() {
    }


    public void handle(Update update, TelegramLongPollingBot bot){
        String groupName = update.getMessage().getText();
        Group exactGroup = groupService.getByExactName(groupName);
        if (exactGroup != null) {
            GregorianCalendar today = mockToday;
            List<Lesson> lessons = lessonService.getByExactDayByGroupName(today.getTime(), exactGroup.getName());

            SendMessage sendMessage = new SendMessage();
            StringBuilder textMessage = new StringBuilder();

            textMessage.append(groupHeader(exactGroup));
            textMessage.append(dateHeader(DayOfWeek.calendarDOWtoDayOfWeek(today.get(Calendar.DAY_OF_WEEK)),today.getTime()));
            textMessage.append(lessonsToString(lessons,today.getTime()));
            sendMessage.setChatId(update.getMessage().getChatId()).setText(textMessage.toString());
            sendMessage.enableHtml(true);
            System.out.println(sendMessage.getText());
            try {
                bot.sendMessage(sendMessage);
            } catch (TelegramApiException e) {
                log.error("messageHandlle", e);
            }
        }
    }

    private String lessonsToString(Collection<Lesson> lessons, Date date){
        StringBuilder lessonListBuilder = new StringBuilder();
        for (Lesson lesson: lessons) {
            lessonListBuilder.append(lessonToString(lesson, date)).append("\n");
        }
        if (lessons.size() == 0 ){
            lessonListBuilder.append(noLessonsMessage());
        }
        return lessonListBuilder.toString();
    }

    public String lessonToString(Lesson l, Date date){
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

    private String dateHeader(DayOfWeek dayOfWeek, Date date){
        StringBuilder headerBuilder = new StringBuilder();
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        headerBuilder.append(DayOfWeek.toReadable(dayOfWeek)).append(" - ").append(format.format(date)).append("\n\n");
        return headerBuilder.toString();
    }

    private String groupHeader(Group group){
        return new StringBuilder().append("<b>").append(group.getName()).append("</b>").append("\n").toString();
    }

    private String noLessonsMessage(){
        return "На текущую дату нет пар!";
    }

}
