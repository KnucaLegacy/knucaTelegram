package com.theopus.knucaTelegram.entity.telegram;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Entity
public class UserTelegram {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name= "increment", strategy= "increment")
    private long id;

    @Transient
    @JsonIgnore
    private final static String no_nickname = "no_nickname";
    @Transient
    private String message;

    @Column
    private String nickname;
    @Column
    private long chatId;
    @Column
    private String firstLastName;


    public UserTelegram() {
    }

    public UserTelegram(String nickname, long chatId, String firstLastName) {
        this.nickname = nickname;
        this.chatId = chatId;
        this.firstLastName = firstLastName;
        this.message = "";
    }

    public UserTelegram(long chatId, String firstLastName) {
        this.chatId = chatId;
        this.firstLastName = firstLastName;
        this.nickname = no_nickname;
        this.message = "";
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
        if (nickname == null)
            this.nickname = no_nickname;
        this.nickname = nickname;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public String getFirstLastName() {
        return firstLastName;
    }

    public void setFirstLastName(String firstLastName) {
        this.firstLastName = firstLastName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
