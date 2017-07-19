package com.theopus.knucaTelegram.bot.action;

import com.theopus.knucaTelegram.bot.command.TelegramMessageFormater;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.Collection;

public abstract class SingleDirSendMessageAction implements SendDataAction {

    protected long chatId;
    protected Collection<SendMessage> messages;
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
}
