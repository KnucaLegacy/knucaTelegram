package com.theopus.knucaTelegram.bot;


import com.theopus.knucaTelegram.bot.command.HelloCommand;
import com.theopus.knucaTelegram.data.entity.Group;
import com.theopus.knucaTelegram.data.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by irina on 05.07.17.
 */

@Service
public class MessageHandleService {

    @Resource
    private GroupService groupService;



    public void handle(Update update, TelegramLongPollingBot bot){
       Set<Group> groupSet = groupService.getAll();
       SendMessage sendMessage = new SendMessage();


    }
}
