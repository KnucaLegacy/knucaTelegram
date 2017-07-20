package com.theopus.knucaTelegram.bot.action;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.theopus.knucaTelegram.bot.util.TelegramMessageFormater;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.Collection;

public abstract class SingleDirSendMessageAction implements SendDataAction {

    @JsonIgnore
    protected long chatId;
    @JsonIgnore
    protected Collection<SendMessage> messages;
    @JsonIgnore
    protected TelegramMessageFormater formater = new TelegramMessageFormater();


    public SingleDirSendMessageAction(long chatId) {
        this.chatId = chatId;
    }

    @Override
    public abstract Collection<SendMessage> buildMessage();

    @Override
    public void execute(AbsSender bot){
        this.messages = buildMessage();
        messages.forEach(message -> {
            message.setChatId(this.chatId);
            try {
                bot.sendMessage(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        });
    }

    public long getChatId() {
        return chatId;
    }
}
