package com.theopus.knucaTelegram.bot.action;

import org.telegram.telegrambots.bots.AbsSender;

public interface Action {

    void execute(AbsSender bot);
}
