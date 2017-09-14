package com.theopus.knucaTelegram.bot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theopus.knucaTelegram.bot.action.Action;
import com.theopus.knucaTelegram.bot.command.HelloCommand;
import com.theopus.knucaTelegram.bot.command.HelpCommand;
import com.theopus.knucaTelegram.bot.command.StartCommand;
import com.theopus.knucaTelegram.entity.telegram.UserTelegram;
import com.theopus.knucaTelegram.service.UserTelegramService;
import io.botan.sdk.Botan;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.ExecutionException;


@Component
public class ScheduleBot extends TelegramLongPollingCommandBot {

    private final Logger log = LoggerFactory.getLogger(this.getClass());



    @Resource
    private MessageActionDispatcher messageDispatcher;
    @Value("${botName}")
    private String botName;

    @Value("${appMetricaToken}")
    private String metricToken;

    @Value("${botToken}")
    private String token;

    @Resource
    private UserTelegramService userTelegramService;

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
        boolean isDirect = true;

        UserTelegram userTelegram = new UserTelegram();
        if (update.hasMessage() && update.getMessage().hasText()) {
            log.info("---------------Message----------------------");
            log.info(update.getMessage().getChat().getUserName());
            Action action = messageDispatcher.handleMessage(update.getMessage().getText(), update.getMessage().getChat().getId(), false);
            action.execute(this);

            userTelegram.setChatId(update.getMessage().getFrom().getId());
            userTelegram.setNickname(update.getMessage().getFrom().getUserName());
            userTelegram.setFirstLastName(
                    update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getFrom().getLastName()
            );
            userTelegram.setMessage(update.getMessage().getText());
            chatId = String.valueOf(update.getMessage().getFrom().getId());
            isDirect = true;

        } else if (update.hasCallbackQuery()){
            log.info("-----------------Callback--------------------------");
            log.info(update.getCallbackQuery().getFrom().getUserName());
            log.info(update.getCallbackQuery().getData());
            Action action = messageDispatcher.handleMessage(update.getCallbackQuery().getData(),  update.getCallbackQuery().getMessage().getChatId(), true);
            action.execute(this);


            userTelegram.setChatId(update.getCallbackQuery().getFrom().getId());
            userTelegram.setNickname(update.getCallbackQuery().getFrom().getUserName());
            userTelegram.setFirstLastName(
                    update.getCallbackQuery().getFrom().getFirstName() + " " + update.getCallbackQuery().getFrom().getLastName()
            );
            userTelegram.setMessage(update.getCallbackQuery().getData());


            chatId = String.valueOf(update.getCallbackQuery().getFrom().getId());
            isDirect = false;
        }

        if (!chatId.equals("")) {
            try (CloseableHttpAsyncClient client = HttpAsyncClients.createDefault()) {
                client.start();
                userTelegramService.saveOne(userTelegram);
                Botan botan = new Botan(client, new ObjectMapper());
                botan.track(metricToken, chatId, userTelegram,
                        isDirect ? "Direct" : "Callback").get();
            } catch (IOException | InterruptedException | ExecutionException e) {
                log.error("err", e);
            }
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
