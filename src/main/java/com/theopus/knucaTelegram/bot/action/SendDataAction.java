package com.theopus.knucaTelegram.bot.action;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.util.Collection;

@Component
public interface SendDataAction extends Action {
    Collection<SendMessage> buildMessage();
}
