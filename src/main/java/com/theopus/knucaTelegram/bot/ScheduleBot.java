package com.theopus.knucaTelegram.bot;

import com.theopus.knucaTelegram.bot.command.HelloCommand;
import com.theopus.knucaTelegram.bot.command.HelpCommand;
import com.theopus.knucaTelegram.bot.command.StartCommand;
import com.theopus.knucaTelegram.data.entity.Group;
import com.theopus.knucaTelegram.data.entity.Lesson;
import com.theopus.knucaTelegram.data.entity.enums.DayOfWeek;
import com.theopus.knucaTelegram.data.service.GroupService;
import com.theopus.knucaTelegram.data.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;
import util.Utils;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by irina on 04.07.17.
 */

@Component
public class ScheduleBot extends TelegramLongPollingCommandBot {

    public static final String LOGTAG = "SCHEDULEBOTHADLER";

    @Resource
    MessageHandleService messageHandleService;

    @Value("${botName}")
    private String botName;

    @Value("${botToken}")
    private String token;

    public ScheduleBot() {
        register(new HelloCommand());
        HelpCommand helpCommand = new HelpCommand(this);
        register(helpCommand);
        register(new StartCommand(this));
        registerDefaultAction((absSender, message) -> {
            SendMessage commandUnknownMessage = new SendMessage();
            commandUnknownMessage.setChatId(message.getChatId());
            commandUnknownMessage.setText("The command '" + message.getText() + "' is not known by this bot. Here comes some help ");
            try {
                absSender.sendMessage(commandUnknownMessage);
            } catch (TelegramApiException e) {
                BotLogger.error(LOGTAG, e);
            }
            helpCommand.execute(absSender, message.getFrom(), message.getChat(), new String[] {});
        });
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            messageHandleService.handle(update, this);
        }
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
