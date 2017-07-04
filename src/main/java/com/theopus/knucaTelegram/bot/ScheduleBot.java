package com.theopus.knucaTelegram.bot;

import com.theopus.knucaTelegram.data.entity.Group;
import com.theopus.knucaTelegram.data.entity.Lesson;
import com.theopus.knucaTelegram.data.service.GroupService;
import com.theopus.knucaTelegram.data.service.LessonService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import util.Utils;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by irina on 04.07.17.
 */

@Component
public class ScheduleBot extends TelegramLongPollingBot {

    @Resource
    private LessonService lessonService;


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            GregorianCalendar gc = new GregorianCalendar();
            int month = gc.get(Calendar.DAY_OF_WEEK);
            System.out.println(month);
            month--;
            month--;
            System.out.println(month);

            List<Lesson> lsit = lessonService.getByWeekDayGroupName(month, messageText);

            try {
                for (Lesson l: lsit) {
                    sendMessage(new SendMessage(update.getMessage().getChatId(),
                            l.toString()));
                }
//                sendMessage(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "KNUCA_Schedule";
    }

    @Override
    public String getBotToken() {
        return "400574726:AAHx1FPNAQyAI3JgqExZNuyZ8KuFIDhYGd4";
    }
}
