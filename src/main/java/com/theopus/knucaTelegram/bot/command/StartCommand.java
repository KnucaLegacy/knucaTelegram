package com.theopus.knucaTelegram.bot.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.bots.commandbot.commands.ICommandRegistry;


public class StartCommand extends BotCommand {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private ICommandRegistry commandRegistry;

    public StartCommand(ICommandRegistry registry) {
        super("start", "start");
        this.commandRegistry = registry;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        commandRegistry.getRegisteredCommand("help").execute(absSender,user, chat, strings);

    }

    @Override
    public String toString() {
        return "/" + this.getCommandIdentifier() + " - " + this.getDescription();
    }


}
