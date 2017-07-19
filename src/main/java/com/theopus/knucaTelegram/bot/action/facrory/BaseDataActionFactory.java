package com.theopus.knucaTelegram.bot.action.facrory;

import com.theopus.knucaTelegram.bot.action.SendDataAction;
import com.theopus.knucaTelegram.bot.action.impl.BadRequest;
import com.theopus.knucaTelegram.bot.action.impl.CorrectSelectData;
import com.theopus.knucaTelegram.bot.action.impl.SendDayData;
import com.theopus.knucaTelegram.bot.action.impl.SendWeekData;
import com.theopus.knucaTelegram.data.entity.enums.DayOfWeek;
import com.theopus.knucaTelegram.data.service.LessonService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Date;

@Service
public class BaseDataActionFactory extends SendDataActionFactory {

    @Resource
    private LessonService lessonService;

    @Override
    SendDataAction sendExactDayDataAction(Object o, long chatId, Date date, int offset) {
        return new SendDayData(chatId, lessonService, o, DayOfWeek.dayDateOffset(date,offset));
    }

    @Override
    SendDataAction sendWeekDataAction(Object o, long chatId, Date date, int offset) {
        return new SendWeekData(chatId, lessonService, o, DayOfWeek.weekDateOffset(date,offset));
    }

    @Override
    SendDataAction sendCorrectSelectionAction(Collection<Object> objects, long chatId) {
        return new CorrectSelectData(chatId, objects);
    }

    @Override
    public SendDataAction sendBadRequest(String message, long chatId) {
        return new BadRequest(chatId, message);
    }
}
