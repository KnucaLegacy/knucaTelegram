package com.theopus.knucaTelegram.service.data.impl;

import com.theopus.knucaTelegram.entity.telegram.UserTelegram;
import com.theopus.knucaTelegram.service.UserTelegramService;
import com.theopus.knucaTelegram.service.data.repository.UserTelegramRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserTelegramServiceImpl implements UserTelegramService {

    @Resource
    private UserTelegramRepository repository;


    @Override
    public UserTelegram getByChatId(long id) {
        return repository.getByChatId(id);
    }

    @Override
    public UserTelegram saveOne(UserTelegram userTelegram) {
        UserTelegram user = repository.getByChatId(userTelegram.getChatId());
        if (user == null)
            return repository.save(userTelegram);
        return user;
    }
}
