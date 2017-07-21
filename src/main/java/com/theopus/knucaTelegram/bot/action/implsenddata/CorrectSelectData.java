package com.theopus.knucaTelegram.bot.action.implsenddata;

import com.theopus.knucaTelegram.bot.action.SingleDirSendMessageAction;
import com.theopus.knucaTelegram.bot.util.KeyboardBuilder;
import com.theopus.knucaTelegram.data.entity.Group;
import com.theopus.knucaTelegram.data.entity.Teacher;
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
        Map<String,String> map = new HashMap<>();
        String callback = "";
        for (Object o : objects) {
            if (o instanceof Group){
                Group g = (Group) o;
                template.setTargetEnt(g);
                callback = template.getCallBackQuery();
                map.put(g.getName(), callback);
            }
            if (o instanceof Teacher){
                Teacher t = (Teacher) o;
                template.setTargetEnt(t);
                callback = template.getCallBackQuery();
                map.put(t.getName(), callback);
            }
        }
        map.forEach((s, s2) -> System.out.println(s + "-" + s2));
        InlineKeyboardMarkup keyBoard = KeyboardBuilder.gridInlineKeyBoard(map, 3);
        Set<SendMessage> result = new LinkedHashSet<>();
        result.add(new SendMessage().enableHtml(true).setReplyMarkup(keyBoard).setText(initialString));
        return result;
    }
}
