package com.theopus.knucaTelegram.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.generics.BotSession;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

@Configuration
@ConditionalOnBean({TelegramLongPollingBot.class, TelegramWebhookBot.class})
public class TelegramBotAutoConfiguration {
    private final Logger logger = LoggerFactory.getLogger(TelegramBotAutoConfiguration.class);
    private List<BotSession> sessions = new ArrayList();
    @Autowired(
            required = false
    )
    private List<TelegramLongPollingBot> pollingBots = new ArrayList();
    @Autowired(
            required = false
    )
    private List<TelegramWebhookBot> webHookBots = new ArrayList();

    public TelegramBotAutoConfiguration() {
    }

    @PostConstruct
    public void start() {
        this.logger.info("Starting auto config for telegram bots");
        TelegramBotsApi api = new TelegramBotsApi();
        this.pollingBots.stream().forEach((bot) -> {
            try {
                this.logger.info("Registering polling bot: {}", bot.getBotUsername());
                this.sessions.add(api.registerBot(bot));
            } catch (TelegramApiException var4) {
                this.logger.error("Failed to register bot {} due to error {}", bot.getBotUsername(), var4.getMessage());
            }

        });
        this.webHookBots.stream().forEach((bot) -> {
            try {
                this.logger.info("Registering web hook bot: {}", bot.getBotUsername());
                api.registerBot(bot);
            } catch (TelegramApiException var4) {
                this.logger.error("Failed to register bot {} due to error {}", bot.getBotUsername(), var4.getMessage());
            }

        });
    }

    @PreDestroy
    public void stop() {
        this.sessions.stream().forEach((session) -> {
            if (session != null) {
                session.stop();
            }

        });
    }

    static {
        ApiContextInitializer.init();
    }
}