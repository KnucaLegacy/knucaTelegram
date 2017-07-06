package com.theopus.knucaTelegram.bot;


import com.theopus.knucaTelegram.bot.command.TelegramMessageFormater;
import com.theopus.knucaTelegram.data.entity.Group;
import com.theopus.knucaTelegram.data.entity.Lesson;
import com.theopus.knucaTelegram.data.entity.enums.DayOfWeek;
import com.theopus.knucaTelegram.data.service.GroupService;
import com.theopus.knucaTelegram.data.service.LessonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


@Service
public class MessageHandleService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private GroupService groupService;
    @Resource
    private LessonService lessonService;
    @Resource
    private TelegramMessageFormater formater;

    private GregorianCalendar mockToday = new GregorianCalendar(2017, 4, 15);

    public MessageHandleService() {
    }


    public void handle(Update update, TelegramLongPollingBot bot){
        handleGroupSingleDate(update, bot);
    }

    private void handleGroupSingleDate(Update update, TelegramLongPollingBot bot){
        String groupName = update.getMessage().getText();
        Group exactGroup = groupService.getByExactName(groupName);
        if (exactGroup != null) {
            GregorianCalendar today = mockToday;
            List<Lesson> lessons = lessonService.getByExactDayByGroupName(today.getTime(), exactGroup.getName());

            SendMessage sendMessage = new SendMessage();
            StringBuilder textMessage = new StringBuilder();

            textMessage.append(formater.groupHeader(exactGroup));
            textMessage.append(formater.dateHeader(DayOfWeek.calendarDOWtoDayOfWeek(today.get(Calendar.DAY_OF_WEEK)),today.getTime()));
            textMessage.append(formater.lessonsToString(lessons,today.getTime()));
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



}
