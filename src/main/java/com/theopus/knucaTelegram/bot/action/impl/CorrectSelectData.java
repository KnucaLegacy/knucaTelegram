package com.theopus.knucaTelegram.bot.action.impl;

import com.theopus.knucaTelegram.bot.action.SendDataAction;
import com.theopus.knucaTelegram.bot.action.SingleDirSendMessageAction;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.util.Collection;

public class CorrectSelectData extends SingleDirSendMessageAction {

    public CorrectSelectData(long chatId, Collection<Object> objects) {
        super(chatId);
    }

    @Override
    public Collection<SendMessage> buildMessage() {
        return null;
    }
}
