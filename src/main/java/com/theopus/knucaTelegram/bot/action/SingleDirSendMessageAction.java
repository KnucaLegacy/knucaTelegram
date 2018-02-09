package com.theopus.knucaTelegram.bot.action;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.theopus.knucaTelegram.bot.util.TelegramMessageFormater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

public abstract class SingleDirSendMessageAction implements SendDataAction {

    @JsonIgnore
    protected long chatId;
    @JsonIgnore
    protected Collection<SendMessage> messages;
    @JsonIgnore
    protected TelegramMessageFormater formater = new TelegramMessageFormater();

    private static final Logger LOG = LoggerFactory.getLogger(SingleDirSendMessageAction.class);

    public SingleDirSendMessageAction(long chatId) {
        this.chatId = chatId;
    }

    @Override
    public abstract Collection<SendMessage> buildMessage();

    @Override
    public void execute(AbsSender bot) {
        this.messages = buildMessage();
        int l =ThreadLocalRandom.current().nextInt(100);

        LOG.info("Add roll = {}", l);
        if (l < 70) {
            messages.add(
                    new SendMessage(
                            0L,
                            "\n Предложения, вопросы? [Официальная группа бота в телеграме.](https://t.me/joinchat/Dlb5FQ7SKH1iEICDdpDN7Q)"
                    )
                            .enableMarkdown(true)
            );
        }
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
