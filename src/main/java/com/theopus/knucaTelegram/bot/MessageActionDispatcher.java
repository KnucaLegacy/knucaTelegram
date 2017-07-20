package com.theopus.knucaTelegram.bot;

import com.theopus.knucaTelegram.bot.action.Action;
import com.theopus.knucaTelegram.bot.action.facrory.SendDataActionFactory;
import com.theopus.knucaTelegram.bot.action.implsenddata.BadRequest;
import com.theopus.knucaTelegram.bot.util.DayOfWeekAllias;
import com.theopus.knucaTelegram.bot.util.DayOffsetValues;
import com.theopus.knucaTelegram.data.entity.Group;
import com.theopus.knucaTelegram.data.entity.enums.DayOfWeek;
import com.theopus.knucaTelegram.data.service.GroupService;
import com.theopus.knucaTelegram.data.service.TeacherService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.Message;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MessageActionDispatcher {

    private Logger log = LoggerFactory.getLogger(MessageActionDispatcher.class);
    @Resource
    private GroupService groupService;
    @Resource
    private TeacherService teacherService;

    @Qualifier("baseDataActionFactory")
    @Resource
    private SendDataActionFactory factory;

    private long chatId = 0;
    private Action action;
    private String messageText;
    private Date currdate = new Date();

    private Pattern exactGroupPattern = Pattern.compile("\\b[А-яІіЇїЄє]{1,6}-(\\S){1,6}\\b");
    private Pattern teacherPattern = Pattern.compile("\\b[^.,\\s\\d]+(\\s[^.,\\d\\s]\\.?)?([^.,\\d\\s]\\.?)?");



    public synchronized Action handleMessage(Message messageObj, Chat chat){
        this.messageText = messageObj.getText();
        this.currdate = new Date();

        parseDate(messageText);
        Matcher matcher = exactGroupPattern.matcher(messageText);
        if (matcher.find()) {
            Action action = exactGroupCase(messageText);
            if (action != null && !(action instanceof BadRequest))
                return action;
        }
        return bot -> {  };
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


    private Set<Date> dates;
    private Pattern numbericDayPattern = Pattern.compile("\\d\\d?(\\p{Punct}|\\s)\\d\\d?");

    private void parseDate(String messageText) {
        dates = new LinkedHashSet<>();
        parseNumericDate(messageText);
        parseDOWDate(messageText);
        parseOffsetDate(messageText);
        dates.forEach(System.out::println);
    }

    private void parseNumericDate(String messageText){
        Matcher m = numbericDayPattern.matcher(messageText);
        while (m.find()) {
            String stringDate = messageText.substring(m.start(), m.end());
            String[] splited = stringDate.split("\\s|\\p{Punct}");
            int day = Integer.parseInt(splited[0]);
            int month = Integer.parseInt(splited[1]);
            int year = new GregorianCalendar().get(Calendar.YEAR);
            dates.add(new GregorianCalendar(year, month - 1, day).getTime());
        }
    }

    private void parseOffsetDate(String messageText) {
        String offsetDate = messageText.toLowerCase();
        for (String s : offsetDate.split("\\s|\\p{Punct}")) {
            Integer integer = DayOffsetValues.whatOffset(s);
            if (integer != null){
                dates.add(DayOfWeek.dayDateOffset(currdate, integer));
            }
        }
    }

    private void parseDOWDate(String messageText) {
        String dowDate = messageText.toLowerCase();
        for (String s : dowDate.split("\\s|\\p{Punct}")) {
            DayOfWeek dayOfWeek = DayOfWeekAllias.whatDayOfWeek(s);
            if (dayOfWeek != null)
                dates.add(DayOfWeek.dayOfweekToDate(dayOfWeek, currdate));
        }
    }

    private String normalize(String initial){
        return StringUtils.replaceChars(initial, "Ии", "Іі").toUpperCase();
    }
}
