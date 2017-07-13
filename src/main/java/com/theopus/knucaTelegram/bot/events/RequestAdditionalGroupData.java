package com.theopus.knucaTelegram.bot.events;

import com.theopus.knucaTelegram.data.entity.Group;

import java.util.Set;

public class RequestAdditionalGroupData implements RequestAdditionalData {

    private long chatId;
    private Set<Group> possibleOptions;

    public RequestAdditionalGroupData(long chatId, Set<Group> possibleOptions) {
        this.chatId = chatId;
        this.possibleOptions = possibleOptions;
    }

    @Override
    public void execute() {
        System.out.println("options : " + possibleOptions);
        System.out.println("send to userPossible options to chat :" + chatId);
    }
}
