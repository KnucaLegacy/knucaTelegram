package com.theopus.knucaTelegram.bot.action.implsenddata;

import com.theopus.knucaTelegram.service.data.SimpleLessonService;
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
                '}';
    }

    @Override
    public String getCallBackQuery() {
        StringBuilder builder = new StringBuilder(super.getCallBackQuery());
        dates.forEach(date1 -> {
            builder.append(simpleDateFormat.format(date1)).append(" ");
        });
        return builder.toString();
    }

    public SendFewDaysData(long chatId, SimpleLessonService service, Object targetEnt, Date date, Set<Date> dates) {
        super(chatId, service, targetEnt, date);
        this.dates = dates;
    }

    @Override
    public Collection<SendMessage> buildMessage() {
        Set<SendMessage> sendMessages = new LinkedHashSet<>();

        dates.forEach(date1 -> {
            this.date = date1;
            sendMessages.add(super.buildMessage().stream().findFirst().orElse(null));
        });
        sendMessages.add(getKeyBoard());
        return sendMessages;
    }
}
