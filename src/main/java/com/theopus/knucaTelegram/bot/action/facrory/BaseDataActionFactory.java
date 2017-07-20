package com.theopus.knucaTelegram.bot.action.facrory;

import com.theopus.knucaTelegram.bot.action.SendDataAction;
import com.theopus.knucaTelegram.bot.action.implsenddata.BadRequest;
import com.theopus.knucaTelegram.bot.action.implsenddata.CorrectSelectData;
import com.theopus.knucaTelegram.bot.action.implsenddata.SendDayData;
import com.theopus.knucaTelegram.bot.action.implsenddata.SendWeekData;
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
        return new SendDayData(chatId, lessonService, o, date,offset);
    }

    @Override
    SendDataAction sendExactDayDataAction(Collection<Object> objects, long chatId, int offset) {
        Object o = objects.stream().findFirst().orElse(null);
        return new CorrectSelectData(objects, new SendDayData(chatId, null, o, null, offset));
    }

    @Override
    SendDataAction sendWeekDataAction(Object o, long chatId, Date date, int offset) {
        return new SendWeekData(chatId, lessonService, o, date, offset);
    }

    @Override
    SendDataAction sendWeekDataAction(Collection<Object> objects, long chatId, int offset) {
        Object o = objects.stream().findFirst().orElse(null);
        return new CorrectSelectData(objects, new SendWeekData(chatId, null, o, null, offset));
    }

    @Override
    public SendDataAction sendBadRequest(String message, long chatId) {
        return new BadRequest(chatId, message);
    }
}
