package com.theopus.knucaTelegram.bot.action.facrory;

import com.theopus.knucaTelegram.bot.action.SendDataAction;
import com.theopus.knucaTelegram.bot.action.implsenddata.*;
import com.theopus.knucaTelegram.service.data.LessonService;
import com.theopus.knucaTelegram.service.data.SimpleLessonService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class BaseDataActionFactory extends SendDataActionFactory {

    @Resource
    private SimpleLessonService lessonService;

    @Override
    public SendDataAction sendDayDataAction(Object o, long chatId, Date date, int offset) {
        return new SendDayData(chatId,lessonService,o,date);
    }

    @Override
    public SendDataAction sendDayDataAction(Object o, long chatId, Collection<Date> dates, int offset) {
        if (dates.size() == 1){
            Date date = dates.stream().findFirst().orElse(null);
            return new SendDayData(chatId,lessonService, o, date);
        }
        if (dates.size() == 2){
            Date[] dateArr = new Date[2];
            final int[] i = {0};
            dates.stream().sorted().forEach(date -> {
                dateArr[i[0]] = date;
                i[0]++;
            });
            return new SendFromToData(chatId,lessonService, o, dateArr[0], dateArr[1]);
        }

        return new SendFewDaysData(chatId, lessonService, o, null, new HashSet<>(dates));
    }

    @Override
    public SendDataAction sendDayDataAction(Collection<Object> objects, long chatId, Collection<Date> dates, int offset, String initial) {
        Object o = objects.stream().findFirst().orElse(null);
        if (dates.size() == 1){
            Date date = dates.stream().findFirst().orElse(null);
            return new CorrectSelectData(objects, new SendDayData(chatId,lessonService, o, date), initial);
        }
        if (dates.size() == 2){
            Date[] dateArr = new Date[2];
            final int[] i = {0};
            dates.stream().sorted().forEach(date -> {
                dateArr[i[0]] = date;
                i[0]++;
            });
            return new CorrectSelectData(objects, new SendFromToData(chatId,lessonService, o, dateArr[0], dateArr[1]), initial);
        }
        return new CorrectSelectData(objects, new SendFewDaysData(chatId, lessonService, o, null, new HashSet<>(dates)), initial);

    }

    @Override
    public SendDataAction sendWeekDataAction(Object o, long chatId, Date date, int offset) {
        return new SendWeekData(chatId, lessonService, o, date);
    }

    @Override
    public SendDataAction sendWeekDataAction(Collection<Object> objects, long chatId, int offset, String initial) {
        Object o = objects.stream().findFirst().orElse(null);
        return new CorrectSelectData(objects, new SendWeekData(chatId, null, o, null), initial);
    }

    @Override
    public SendDataAction sendBadRequest(String message, long chatId) {
        return new BadRequest(chatId, message);
    }
}
