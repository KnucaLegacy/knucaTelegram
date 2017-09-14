package com.theopus.knucaTelegram.service;

import com.theopus.knucaTelegram.entity.telegram.UserTelegram;


public interface UserTelegramService {

    UserTelegram getByChatId(long id);
    UserTelegram saveOne(UserTelegram userTelegram);

}
