package com.theopus.knucaTelegram.bot.action;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ResponseDataAction implements Action {

    private static final String genericName = "action:response_data_action:";

    private String actionName;
    private long chatId;
    private List<SendMessage> messageQuery;


    public ResponseDataAction(String actionName, long chatId, Collection<SendMessage> messageQuery) {
        this.actionName = actionName;
        this.chatId = chatId;
        this.messageQuery = new ArrayList<>(messageQuery);
    }

    public ResponseDataAction(String actionName, long chatId, SendMessage messageQuery) {
        this.actionName = actionName;
        this.chatId = chatId;
        this.messageQuery = new ArrayList<>();
        this.messageQuery.add(messageQuery);
    }

    @Override
    public void execute(AbsSender bot) {
        if (messageQuery != null)
            messageQuery.forEach(sendMessage -> {
                sendMessage.setChatId(this.chatId);
                try {
                    bot.sendMessage(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            });
    }

}
