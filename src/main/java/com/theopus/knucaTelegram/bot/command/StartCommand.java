package com.theopus.knucaTelegram.bot.command;

import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.bots.commandbot.commands.CommandRegistry;
import org.telegram.telegrambots.bots.commandbot.commands.ICommandRegistry;

/**
 * Created by irina on 05.07.17.
 */
public class StartCommand extends BotCommand {

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
