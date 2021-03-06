package com.theopus.knucaTelegram.bot.action.implsenddata;

import com.theopus.knucaTelegram.bot.action.CallBackable;
import com.theopus.knucaTelegram.bot.action.SingleDirSendMessageAction;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class BadRequest extends SingleDirSendMessageAction implements CallBackable {

    private String message;

    public BadRequest(long chatId, String message) {
        super(chatId);
        this.message = message;
    }

    @Override
    public String toString() {
        return "BadRequest{" +
                "message='" + message + '\'' +
                '}';
    }

    @Override
    public String getCallBackQuery() {
       return toString();
    }

    @Override
    public Collection<SendMessage> buildMessage() {
        Set<SendMessage> result = new HashSet<>();
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableHtml(true).setText("Я не понимаю что это значит: " + message + "\".");
        result.add(sendMessage);
        return result;
    }
}
