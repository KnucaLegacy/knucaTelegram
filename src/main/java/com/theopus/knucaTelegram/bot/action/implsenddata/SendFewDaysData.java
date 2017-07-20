package com.theopus.knucaTelegram.bot.action.implsenddata;

import com.theopus.knucaTelegram.data.service.LessonService;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

public class SendFewDaysData extends SendDayData {

    private Set<Date> dates;

    @Override
    public String toString() {
        return "SendFewDaysData{" +
                "dates=" + dates +
                ", targetEnt=" + targetEnt +
                ", offset=" + offset +
                '}';
    }

    public SendFewDaysData(long chatId, LessonService service, Object targetEnt, Date date, int offset, Set<Date> dates) {
        super(chatId, service, targetEnt, date, offset);
        this.dates = dates;
    }

    @Override
    public Collection<SendMessage> buildMessage() {
        Set<SendMessage> sendMessages = new LinkedHashSet<>();

        dates.forEach(date1 -> {
            this.date = date1;
            sendMessages.addAll(super.buildMessage());
        });
        return sendMessages;
    }
}
