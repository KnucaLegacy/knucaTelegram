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


public class StartCommand extends BotCommand {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private ICommandRegistry commandRegistry;

    public StartCommand(ICommandRegistry registry) {
        super("start", "start");
        this.commandRegistry = registry;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
//        commandRegistry.getRegisteredCommand("help").execute(absSender,user, chat, strings);
        StringBuilder helpMessageBuilder = new StringBuilder("");
        helpMessageBuilder.append("Что может делать этот бот? \n");
        helpMessageBuilder.append("Просто напиши свою группу :)\n");
        SendMessage helpMessage = new SendMessage();
        helpMessage.setChatId(chat.getId().toString());
        helpMessage.enableHtml(true);
        helpMessage.setText(helpMessageBuilder.toString());

        try {
            absSender.sendMessage(helpMessage);
        } catch (TelegramApiException e) {
            log.error("errorSendingHelpMessage",e);
        }

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chat.getId());
        sendMessage.setText("Введи свою грппу в формате <b>[аббревиатура]-[номер] \n на укр. языке</b> , например : " +
                "іуст-31," +
                "арх-12а");
        sendMessage.enableHtml(true);
        try {
            absSender.sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            log.error("STARTCOMANDEXECUTE", e);
        }

    }

    @Override
    public String toString() {
        return "/" + this.getCommandIdentifier() + " - " + this.getDescription();
    }


}
