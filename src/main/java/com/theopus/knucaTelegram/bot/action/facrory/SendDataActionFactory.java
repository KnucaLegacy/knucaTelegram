package com.theopus.knucaTelegram.bot.action.facrory;

import com.theopus.knucaTelegram.bot.action.SendDataAction;

import java.util.Collection;
import java.util.Date;


public abstract class SendDataActionFactory {


    abstract public SendDataAction sendDayDataAction(Object o, long chatId, Date date, int offset);

    abstract public SendDataAction sendDayDataAction(Object o, long chatId, Collection<Date> dates, int offset);

    abstract public SendDataAction sendDayDataAction(Collection<Object> objects, long chatId, Collection<Date> dates, int offset, String initial);

    abstract public SendDataAction sendWeekDataAction(Object o, long chatId, Date date, int offset);

    abstract public SendDataAction sendWeekDataAction(Collection<Object> objects, long chatId, int offset, String initial);

    abstract public SendDataAction sendBadRequest(String message, long chatId);
}
