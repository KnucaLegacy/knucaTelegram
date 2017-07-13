package com.theopus.knucaTelegram.bot.events;

import com.theopus.knucaTelegram.data.entity.Group;

public class SendDataByGroup implements SendData {

    private long chatId;
    private Group group;

    public SendDataByGroup(long chatId, Group group) {
        this.chatId = chatId;
        this.group = group;
    }

    @Override
    public void execute() {
        System.out.println("get group data... for" + group);
        System.out.println("send group data to" + chatId);
    }
}
