package com.theopus.knucaTelegram.bot.action.implsenddata;

import com.theopus.knucaTelegram.bot.action.SingleDirSendMessageAction;
import com.theopus.knucaTelegram.bot.util.KeyboardBuilder;
import com.theopus.knucaTelegram.entity.schedule.Group;
import com.theopus.knucaTelegram.entity.schedule.Teacher;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.util.*;

public class SendInlineKeyboard extends SingleDirSendMessageAction {

    private Object targetEnt;

    private static final String TODAY_TITLE = "Сегодня";
    private static final String TOMORROW_TITLE ="Завтра";
    private static final String WEEK_TITLE = "Неделя";
    private static final String NEXTWEEK_TITLE = "След. неделя";

    public SendInlineKeyboard(long chatId, Object targetEnt) {
        super(chatId);
        this.targetEnt = targetEnt;
    }

    @Override
    public Collection<SendMessage> buildMessage() {

        Map<String, String> keyMap = new LinkedHashMap<>();

        String today = "";
        String tomorrow ="";
        String week = "";
        String nextWeek = "";

        String name = "";

        if (targetEnt instanceof Group){
            Group group = (Group) targetEnt;
            name = group.getName();

        }
        if (targetEnt instanceof Teacher){
            Teacher teacher = (Teacher) targetEnt;
            name = teacher.getName();
        }
        today = name;
        tomorrow = name + " " + "tommorow";
        week = name + " " + "week";
        nextWeek = name + " " + "nextweek";

        keyMap.put(TODAY_TITLE, today);
        keyMap.put(TOMORROW_TITLE, tomorrow);
        keyMap.put(WEEK_TITLE, week);
        keyMap.put(NEXTWEEK_TITLE, nextWeek);

        return new LinkedHashSet<>(Collections.singletonList(new SendMessage()
                .setText(name)
                .setReplyMarkup(KeyboardBuilder.gridInlineKeyBoard(keyMap, 2))));
    }
}
