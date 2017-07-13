package com.theopus.knucaTelegram.bot.events;

public class BadRequest implements Event {

    private long chatID;

    public BadRequest(long chatID) {
        this.chatID = chatID;
    }

    @Override
    public void execute() {
        System.out.println("send to" + chatID + "bad request");
    }
}
