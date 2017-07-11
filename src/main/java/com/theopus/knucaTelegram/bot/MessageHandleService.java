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
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import javax.annotation.Resource;
import java.util.*;


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

    public void handleMessage(Message message, TelegramLongPollingBot bot){

    }


    public void handle(Message msg, String data, TelegramLongPollingBot bot){
        String message;
        if (data == null)
            message = msg.getText();
        else
            message = data;
        System.out.println(message);
        Group exactGroup = groupService.getByExactName(message);

        if (exactGroup != null){
            handleGroupSingleDate(msg, data,  bot);
            return;
        }
        Set<Group> groupsAllies = groupService.getByAlliesName(message);
        if (groupsAllies != null && !groupsAllies.isEmpty()){
            SendMessage sendMessage = new SendMessage()
                    .setChatId(msg.getChatId())
                    .setText("Похожие на " + message + " ");
            InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

            List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
            List<InlineKeyboardButton> rowInline = new ArrayList<>();

            int i = 0;
            for (Group g: groupsAllies) {
                if (i%3 == 0){
                    rowsInline.add(rowInline);
                    rowInline = new ArrayList<>();
                }
                rowInline.add(new InlineKeyboardButton().setText(g.getName()).setCallbackData(g.getName()));
                i++;
            }
            rowsInline.add(rowInline);
            // Add it to the message
            markupInline.setKeyboard(rowsInline);
            sendMessage.setReplyMarkup(markupInline);
            try {
                bot.sendMessage(sendMessage);
            } catch (TelegramApiException e) {
                log.error("inline allies", e);
            }
            return;
        }

        try {
            bot.sendMessage(new SendMessage().setChatId(msg.getChatId()).setText("Мая не панимать"));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void handleGroupSingleDate(Message message, String data, TelegramLongPollingBot bot){
        String groupName;
        System.out.println("data" + data);
        if (data == null)
            groupName = message.getText();
        else
            groupName = data;
        Group exactGroup = groupService.getByExactName(groupName);
        System.out.println("exact group" + exactGroup);
        if (exactGroup != null) {
            GregorianCalendar today = mockToday;
            List<Lesson> lessons = lessonService.getExactDayByGroup(today.getTime(), exactGroup.getName());

            SendMessage sendMessage = new SendMessage();
            StringBuilder textMessage = new StringBuilder();

            textMessage.append(formater.groupHeader(exactGroup));
            textMessage.append(formater.dateHeader(DayOfWeek.calendarDOWtoDayOfWeek(today.get(Calendar.DAY_OF_WEEK)),today.getTime()));
            textMessage.append(formater.lessonsToString(lessons,today.getTime()));
            sendMessage.setChatId(message.getChatId()).setText(textMessage.toString());
            sendMessage.enableHtml(true);
            try {
                bot.sendMessage(sendMessage);
            } catch (TelegramApiException e) {
                log.error("messageHandlle", e);
            }
        }
    }



}
