package com.theopus.knucaTelegram.bot.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.bots.commandbot.commands.ICommandRegistry;
import org.telegram.telegrambots.exceptions.TelegramApiException;


public class HelpCommand extends BotCommand {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final ICommandRegistry commandRegistry;

    public HelpCommand(ICommandRegistry commandRegistry) {
        super("help", "Get all the commands this bot provides");
        this.commandRegistry = commandRegistry;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

        StringBuilder helpMessageBuilder = new StringBuilder("<b>Help</b>\n");
        helpMessageBuilder.append("Что может делать этот бот?\n");
        helpMessageBuilder.append("Просто напиши свою группу :)\n");

        for (BotCommand botCommand : commandRegistry.getRegisteredCommands()) {
            if (botCommand.getCommandIdentifier() != "start")
                helpMessageBuilder.append(botCommand.toString()).append("\n");
        }


        SendMessage helpMessage = new SendMessage();
        helpMessage.setChatId(chat.getId().toString());
        helpMessage.enableHtml(true);
        helpMessage.setText(helpMessageBuilder.toString());

        try {
            absSender.sendMessage(helpMessage);
        } catch (TelegramApiException e) {
            log.error("errorSendingHelpMessage",e);
        }
    }

    @Override
    public String toString() {
        return "/" + this.getCommandIdentifier() + " - " + this.getDescription();
    }
}
