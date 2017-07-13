package com.theopus.knucaTelegram.bot;

import com.theopus.knucaTelegram.bot.events.*;
import com.theopus.knucaTelegram.data.entity.Group;
import com.theopus.knucaTelegram.data.service.GroupService;
import com.theopus.knucaTelegram.data.service.impl.GroupServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;
import java.util.regex.Pattern;

@Service
public class MessageEventDispatcher {


    private Pattern pattern = Pattern.compile("\\b[А-яІіЇїЄє]{1,6}-(\\S){1,6}\\b");
    @Qualifier("groupServiceImpl")
    @Resource
    private GroupService groupService;

    private long chatId = 0;

    public Event handleMessage(String messageText, long chatId){

        this.chatId = chatId;

        if (messageText.contains("-"))
            return exactGroupMathcing(messageText);
        Pattern exactGroupPattern = Pattern.compile("\\b[А-яІіЇїЄє]{1,6}-([\\dА-яІіЇїЄє]){1,6}\\b");

        return new BadRequest(chatId);
    }

//    public Event notExactGroupMatching(String group){
//
//    }

    public Event exactGroupMathcing(String exactGroup){
        Group group = groupService.getByExactName(exactGroup);
        if (group != null)
            return new SendDataByGroup(chatId, group);
        else
            return alliesGroupMathcing(exactGroup);
    }


    public Event alliesGroupMathcing(String alliesGroup){
        Set<Group> groupSet = groupService.getByAlliesName(alliesGroup);
        if (groupSet.isEmpty() || groupSet == null)
            return new BadRequest(chatId);
        else
            return new RequestAdditionalGroupData(chatId,groupSet);
    }
}
