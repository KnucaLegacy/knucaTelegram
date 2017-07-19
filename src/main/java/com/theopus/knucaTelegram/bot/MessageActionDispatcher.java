package com.theopus.knucaTelegram.bot;

import com.theopus.knucaTelegram.bot.action.Action;
import com.theopus.knucaTelegram.bot.action.facrory.SendDataActionFactory;
import com.theopus.knucaTelegram.data.entity.Group;
import com.theopus.knucaTelegram.data.service.GroupService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.Message;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Set;
import java.util.regex.Pattern;

@Service
public class MessageActionDispatcher {

    private Logger log = LoggerFactory.getLogger(MessageActionDispatcher.class);
    private Pattern pattern = Pattern.compile("\\b[А-яІіЇїЄє]{1,6}-(\\S){1,6}\\b");

    @Resource
    private GroupService groupService;

    public String groupsList;

    @Qualifier("baseDataActionFactory")
    @Autowired
    private SendDataActionFactory factory;


    @PostConstruct
    private void loadStringQuery(){
        Set<Group> groups = groupService.getAll();
        StringBuilder sb = new StringBuilder();
        groups.forEach(group -> sb.append(group.getName()).append(";"));
        groupsList = sb.toString().toUpperCase();
    }


    private long chatId = 0;

    public synchronized Action handleMessage(Message messageObj, Chat chat){
        this.chatId = chat.getId();
        String message = messageObj.getText();
        message = message.toUpperCase();
        this.chatId = chatId;

        if (message.contains("-"))
            return exactGroupMathcing(message);
        notExactGroupMatching(message);
        return factory.sendBadRequest(message,chatId);
    }

    public Action notExactGroupMatching(String group){
        String groupName = normalize(group);
        String tmp = groupName;
        for (int i = 0; i < groupName.length(); i++) {
            if(!groupsList.contains(tmp)) {
                tmp = groupName.substring(0,groupName.length() - i);
                continue;
            }
            else
                return exactGroupMathcing(tmp);
        }
        return factory.sendBadRequest(group, chatId);
    }

    public Action exactGroupMathcing(String exactName){
        String groupName = normalize(exactName);
        System.out.println(groupName);
        Group group = groupService.getByExactName(groupName);
        if (group != null){
            return factory.sendBadRequest(exactName, chatId);
        }
        else
            return alliesGroupMathcing(groupName);
    }


    public Action alliesGroupMathcing(String groupName){
        String alliesGroup = normalize(groupName);
        Set<Group> groupSet = groupService.getByAlliesName(alliesGroup);
        if (groupSet.isEmpty() || groupSet == null)
            factory.sendBadRequest(groupName, chatId);
        else{
            log.info("event complition at chatId" + chatId);
            return factory.sendBadRequest(groupName,chatId);
        }
        return factory.sendBadRequest(groupName,chatId);
    }

    public String normalize(String initial){
        return StringUtils.replaceChars(initial, "Ии", "Іі").toUpperCase();
    }
}
