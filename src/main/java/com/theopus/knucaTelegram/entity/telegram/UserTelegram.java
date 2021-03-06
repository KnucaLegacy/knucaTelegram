package com.theopus.knucaTelegram.entity.telegram;

public class UserTelegram {


    private long id;

    private final static String no_nickname = "no_nickname";

    private String nickname;

    private long chatId;

    public UserTelegram() {
    }

    public UserTelegram(String nickname, long chatId) {
        this.nickname = nickname;
        this.chatId = chatId;
    }

    public UserTelegram(long chatId) {
        this.nickname = no_nickname;
        this.chatId = chatId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static String getNo_nickname() {
        return no_nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }
}
