package com.theopus.knucaTelegram.bot.action.implsenddata;

import com.theopus.knucaTelegram.bot.action.SingleDirSendMessageAction;
import com.theopus.knucaTelegram.bot.util.KeyboardBuilder;
import com.theopus.knucaTelegram.entity.schedule.Group;
import com.theopus.knucaTelegram.entity.schedule.Teacher;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.*;

public class CorrectSelectData extends SingleDirSendMessageAction {

    private Collection<Object> objects;
    private SendDayData template;
    private String initialString;

    public CorrectSelectData(Collection<Object> objects, SendDayData sendDayData, String initialString) {
        super(sendDayData.getChatId());
        this.objects = objects;
        this.template = sendDayData;
        this.initialString = initialString;
    }

    @Override
    public Collection<SendMessage> buildMessage() {
        Map<String, String> map = new TreeMap<>();
        String callback = "";
        for (Object o : objects) {
            if (o instanceof Group) {
                Group g = (Group) o;
                template.setTargetEnt(g);
                callback = template.getCallBackQuery();
                map.put(g.getName(), callback);
            }
            if (o instanceof Teacher) {
                Teacher t = (Teacher) o;
                template.setTargetEnt(t);
                callback = template.getCallBackQuery();
                map.put(t.getName(), callback);
            }
        }
        Set<SendMessage> result = new LinkedHashSet<>();


        if (map.size() > 80) {
            Map<String, String> map2 = new TreeMap<>();
            Map<String, String> map25 = new TreeMap<>();


            map.entrySet()
                    .stream().limit(80)
                    .forEach(s -> map2.put(s.getKey(), s.getValue()));


            map.entrySet()
                    .stream().skip(80)
                    .forEach(s -> map25.put(s.getKey(), s.getValue()));


            InlineKeyboardMarkup keyBoard2 = KeyboardBuilder.gridInlineKeyBoard(map2, 3);
            InlineKeyboardMarkup keyBoard25 = KeyboardBuilder.gridInlineKeyBoard(map25, 3);
            result.add(new SendMessage().enableHtml(true).setReplyMarkup(keyBoard2).setText(initialString));
            result.add(new SendMessage().enableHtml(true).setReplyMarkup(keyBoard25).setText(initialString));
        } else {
            InlineKeyboardMarkup keyBoard = KeyboardBuilder.gridInlineKeyBoard(map, 3);
            result.add(new SendMessage().enableHtml(true).setReplyMarkup(keyBoard).setText(initialString));
        }



        return result;
    }
}
