package com.theopus.knucaTelegram.bot.action.facrory;

import com.theopus.knucaTelegram.bot.action.Action;
import com.theopus.knucaTelegram.bot.action.SendDataAction;

import java.util.Collection;
import java.util.Date;


public abstract class SendDataActionFactory {

    abstract SendDataAction sendExactDayDataAction(Object o, long chatId, Date date, int offset);

    abstract SendDataAction sendExactDayDataAction(Collection<Object> objects, long chatId, int offset);

    abstract SendDataAction sendWeekDataAction(Object o, long chatId, Date date, int offset);

    abstract SendDataAction sendWeekDataAction(Collection<Object> objects, long chatId, int offset);

    abstract public SendDataAction sendBadRequest(String message, long chatId);
}
