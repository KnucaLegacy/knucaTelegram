package com.theopus.knucaTelegram.bot.action.facrory;

import com.theopus.knucaTelegram.bot.action.Action;

import java.util.Collection;
import java.util.Date;


public abstract class SendDataActionFactory {

    abstract Action sendExactDayDataAction(Object o, long chatId, Date date, int offset);
    abstract Action sendWeekDataAction(Object o, long chatId, Date date, int offset);
    abstract Action sendCorrectSelectionAction(Collection<Object> objects, long chatId);
    abstract public Action sendBadRequest(String message, long chatId);
}
