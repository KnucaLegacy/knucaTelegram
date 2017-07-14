package com.theopus.knucaTelegram.bot.action;

import org.telegram.telegrambots.api.methods.send.SendMessage;

public class BadRequestResponseAction extends ResponseDataAction {

    public BadRequestResponseAction(String actionName, long chatId, SendMessage messageQuery) {
        super(actionName, chatId, messageQuery);
    }
}
