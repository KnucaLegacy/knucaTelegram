package com.theopus.knucaTelegram.bot;

import com.theopus.knucaTelegram.bot.command.TelegramMessageFormater;
import com.theopus.knucaTelegram.data.entity.Group;
import com.theopus.knucaTelegram.data.entity.Lesson;
import com.theopus.knucaTelegram.data.entity.enums.DayOfWeek;
import com.theopus.knucaTelegram.data.repository.LessonRepository;
import com.theopus.knucaTelegram.data.service.LessonService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class GroupLessonMessageFactory {

    @Resource
    private LessonService lessonService;
    @Resource
    private TelegramMessageFormater telegramMessageFormater;


    private SendMessage oneDayDataMessage(long userId, Date date, Group group, int offset){
        SendMessage result = new SendMessage();
        result.setChatId(userId).enableHtml(true);
        Date trueDate = DayOfWeek.dayDateOffset(date,offset);
        StringBuilder mesg = new StringBuilder();
        mesg.append(telegramMessageFormater.dateHeader(trueDate));
        mesg.append(telegramMessageFormater.lessonsToString(lessonService.getExactDayByGroup(trueDate,group,0),DayOfWeek.dayDateOffset(trueDate,offset)));
        result.setText(mesg.toString());
        return result;
    }

    public List<SendMessage> oneWeekDataMessage(long chatId, Date date, Group group, int week){
        Map<DayOfWeek, Date> dateMap = DayOfWeek.dateToDateMap(date);
        Map<DayOfWeek, List<Lesson>> lessonMap = lessonService.getWeekByGroup(date,group,week);
        List<SendMessage> sendMessages =  new ArrayList<>();

        for (Map.Entry<DayOfWeek, Date> dateMapPair: dateMap.entrySet()){
            if (!lessonMap.get(dateMapPair.getKey()).isEmpty()) {
                Date pairDate = dateMapPair.getValue();
                SendMessage sendMessage = new SendMessage();
                sendMessage.enableHtml(true);
                sendMessage.setChatId(chatId);
                StringBuilder mesg = new StringBuilder();
                mesg.append(telegramMessageFormater.groupHeader(group));
                mesg.append(telegramMessageFormater.dateHeader(pairDate));
                mesg.append(telegramMessageFormater.lessonsToString(lessonMap.get(dateMapPair.getKey()), pairDate));
                sendMessage.setText(mesg.toString());
                sendMessages.add(sendMessage);
            }
        }

        return sendMessages;
    }
}
