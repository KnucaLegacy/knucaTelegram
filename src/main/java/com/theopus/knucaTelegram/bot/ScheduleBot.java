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
        String chatId = "";
        String message = "";
        boolean isDirect = true;

        if (update.hasMessage() && update.getMessage().hasText()) {
            log.info("---------------Message----------------------");
            log.info(update.getMessage().getChat().getUserName());
            Action action = messageDispatcher.handleMessage(update.getMessage().getText(), update.getMessage().getChat().getId(), false);
            action.execute(this);

            message = update.getMessage().getText();
            chatId = update.getCallbackQuery().getId();
            isDirect = true;

        } else if (update.hasCallbackQuery()){
            log.info("-----------------Callback--------------------------");
            log.info(update.getCallbackQuery().getFrom().getUserName());
            log.info(update.getCallbackQuery().getData());
            Action action = messageDispatcher.handleMessage(update.getCallbackQuery().getData(),  update.getCallbackQuery().getMessage().getChatId(), true);
            action.execute(this);

            message = update.getCallbackQuery().getData();
            chatId = update.getCallbackQuery().getId();
            isDirect = false;
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
