package com.theopus.knucaTelegram.bot.botanalytics;

import org.springframework.stereotype.Service;

@Service
public class BotAnalytics {

//    @Value("appMetricaToken")
//    private String token;
//
//    public void trackCommand(Class<BotCommand> command, String uid, String message){
//        try (CloseableHttpAsyncClient client = HttpAsyncClients.createDefault()) {
//            client.start();
//            Botan botan = new Botan(client, new ObjectMapper());
//            botan.track(token, uid, message, command.getSimpleName()).get();
//            client.close();
//        } catch (NullPointerException | IOException | ExecutionException | InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void trackNonComand(ActionType actionType, long uid, String message){
//        try (CloseableHttpAsyncClient client = HttpAsyncClients.createDefault()) {
//            client.start();
//            Botan botan = new Botan(client, new ObjectMapper());
//            botan.track(token, String.valueOf(uid), message, actionType.name()).get();
//            client.close();
//        } catch (NullPointerException | IOException | ExecutionException | InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
}
