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
        sendMessage.setText("Для расписания на сегодня, отправь свою грппу в формате: \n" +
                "<b>[аббревиатура]-[номер] \n на укр. языке</b>, например : іуст-31," + "арх-12а.\n" +
                "Также,с группой можешь указать конкретную дату (напр.: 12.04) или несколько дат (напр.:  25.04 26.04 29.04), \n" +
                "либо какой-то период дат (напр.: 5.05-8.05).");
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
