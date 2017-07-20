package com.theopus.knucaTelegram.bot;

import com.theopus.knucaTelegram.bot.action.Action;
import com.theopus.knucaTelegram.bot.action.facrory.SendDataActionFactory;
import com.theopus.knucaTelegram.bot.action.impl.BadRequest;
import com.theopus.knucaTelegram.data.entity.Group;
import com.theopus.knucaTelegram.data.service.GroupService;
import com.theopus.knucaTelegram.data.service.TeacherService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.Message;

import javax.annotation.Resource;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MessageActionDispatcher {

    private Logger log = LoggerFactory.getLogger(MessageActionDispatcher.class);
    @Resource
    private GroupService groupService;
    @Resource
    private TeacherService teacherService;

    public String groupsList;

    @Qualifier("baseDataActionFactory")
    @Autowired
    private SendDataActionFactory factory;

    private long chatId = 0;
    private Action action;
    private String messageText;
    private Date date;

    private Pattern exactGroupPattern = Pattern.compile("\\b[А-яІіЇїЄє]{1,6}-(\\S){1,6}\\b");
    private Pattern teacherPattern = Pattern.compile("\\b[^.,\\s\\d]+(\\s[^.,\\d\\s]\\.?)?([^.,\\d\\s]\\.?)?");
    public synchronized Action handleMessage(Message messageObj, Chat chat){
        this.messageText = messageObj.getText();
        date = parseDate(messageText);


        Matcher matcher = exactGroupPattern.matcher(messageText);
        if (matcher.find()) {
            Action action = exactGroupCase(messageText);
            if (action != null && !(action instanceof BadRequest))
                return action;
        }
        return bot -> {  };
    }



    private Pattern numbericdayPattern = Pattern.compile("\\d\\d?(\\p{Punct}|\\s)\\d\\d?");

    private Date parseDate(String messageText) {
        return null;
    }


    public Action notExactGroupCase(String text){
        return null;
    }

    public Action exactGroupCase(String text){
        String exactGroupName = normalize(text);
        Group group = groupService.getByExactName(exactGroupName);
        if (group != null){

//            factory
        }
        return null;
    }


    public Action alliesGroupMathcing(String groupName){
        return null;
    }

    public String normalize(String initial){
        return StringUtils.replaceChars(initial, "Ии", "Іі").toUpperCase();
    }
}
