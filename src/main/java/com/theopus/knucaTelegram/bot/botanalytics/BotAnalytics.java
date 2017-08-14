package com.theopus.knucaTelegram.bot.botanalytics;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.botan.sdk.Botan;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Service
public class BotAnalytics {

    @Value("appMetricaToken")
    private String token;

    public void trackCommand(Class<BotCommand> command, String uid, String message){
        try (CloseableHttpAsyncClient client = HttpAsyncClients.createDefault()) {
            client.start();
            Botan botan = new Botan(client, new ObjectMapper());
            botan.track(token, uid, message, command.getSimpleName()).get();
            client.close();
        } catch (NullPointerException | IOException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void trackNonComand(ActionType actionType, long uid, String message){
        try (CloseableHttpAsyncClient client = HttpAsyncClients.createDefault()) {
            client.start();
            Botan botan = new Botan(client, new ObjectMapper());
            botan.track(token, String.valueOf(uid), message, actionType.name()).get();
            client.close();
        } catch (NullPointerException | IOException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
