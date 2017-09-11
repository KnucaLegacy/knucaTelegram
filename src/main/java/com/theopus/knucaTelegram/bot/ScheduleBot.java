package com.theopus.knucaTelegram.bot;

import com.theopus.knucaTelegram.bot.action.Action;
import com.theopus.knucaTelegram.bot.command.HelloCommand;
import com.theopus.knucaTelegram.bot.command.HelpCommand;
import com.theopus.knucaTelegram.bot.command.StartCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import javax.annotation.Resource;


@Component
public class ScheduleBot extends TelegramLongPollingCommandBot {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private MessageActionDispatcher messageDispatcher;
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
                log.error("errorSendingHelpMessage", e);
            }
            helpCommand.execute(absSender, message.getFrom(), message.getChat(), new String[] {});
        });
    }


    @Override
    public void processNonCommandUpdate(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            System.out.println(update.getMessage().getChat().getUserName());
            Action action = messageDispatcher.handleMessage(update.getMessage().getText(), update.getMessage().getChat().getId(), false);
            action.execute(this);

        } else if (update.hasCallbackQuery()){
            System.out.println(update.getCallbackQuery().getData());
            Action action = messageDispatcher.handleMessage(update.getCallbackQuery().getData(),  update.getCallbackQuery().getMessage().getChatId(), true);
            action.execute(this);
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
