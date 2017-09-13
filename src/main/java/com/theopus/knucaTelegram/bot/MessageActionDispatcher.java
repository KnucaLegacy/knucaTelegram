package com.theopus.knucaTelegram.bot;

import com.theopus.knucaTelegram.bot.action.Action;
import com.theopus.knucaTelegram.bot.action.facrory.SendDataActionFactory;
import com.theopus.knucaTelegram.bot.action.implsenddata.BadRequest;
import com.theopus.knucaTelegram.bot.botanalytics.ActionType;
import com.theopus.knucaTelegram.bot.botanalytics.BotAnalytics;
import com.theopus.knucaTelegram.bot.util.DayOfWeekAllias;
import com.theopus.knucaTelegram.bot.util.DayOffsetValues;
import com.theopus.knucaTelegram.entity.schedule.Group;
import com.theopus.knucaTelegram.entity.schedule.Teacher;
import com.theopus.knucaTelegram.entity.schedule.enums.DayOfWeek;
import com.theopus.knucaTelegram.service.data.GroupService;
import com.theopus.knucaTelegram.service.data.TeacherService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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
    @Resource
    private BotAnalytics botAnalytics;

    private long chatId = 0;
    private Action action;
    private String messageText;
    private Date currdate;
    private boolean reqiereWeek;
    private boolean isCallBack = false;

    private Pattern exactGroupPattern = Pattern.compile("\\b[А-яІіЇїЄє]{1,6}-(\\S){1,6}\\b");
    private Pattern teacherPattern = Pattern.compile("\\b[^.,\\s\\d]+(\\s[^.,\\d\\s]\\.?)?([^.,\\d\\s]\\.?)?");
    private Pattern exactTeacherPattern = Pattern.compile("\\b((([^.,\\s\\d\\p{Punct}]{2,5}.)?[^.,\\s\\d\\p{Punct}]{2,4}\\.)|[^.,\\d\\s]{3,}\\.)\\s[^.,\\s\\d]+(\\s[^.,\\d\\s]\\.)?([^.,\\d\\s]\\.?)?");



    public synchronized Action handleMessage(String messageText, long chatId, boolean isCallback){

        String extratedData = extractData(messageText);
        log.info("---message---" + messageText);
        log.info("-----------------------------------------------");
        this.isCallBack = isCallback;
        this.chatId = chatId;
        this.messageText = messageText;
        this.currdate = DayOfWeek.dateToRawDate(new Date());
        this.reqiereWeek = parseWeek(messageText);
        parseDate(extratedData);

        Action tmpaction;

        Matcher matcher = exactGroupPattern.matcher(messageText);
        if (matcher.find()){
            tmpaction = exactGroupCase(messageText.substring(matcher.start(),matcher.end()));
            if (tmpaction != null && !(tmpaction instanceof BadRequest))
                return tmpaction;
        }

        matcher = exactTeacherPattern.matcher(messageText);
        if (matcher.find()) {
            tmpaction = exactTeacherCase(messageText.substring(matcher.start(), matcher.end()));
            if (tmpaction != null && !(tmpaction instanceof BadRequest))
                return tmpaction;
        }

        String[] messagewords = messageText.split("\\s");
        for (String word : messagewords) {
            tmpaction = notExactGroupCase(word);
            if (tmpaction != null && !(tmpaction instanceof BadRequest))
                return tmpaction;
            tmpaction = notExactTeacherCase(word);
            if (tmpaction != null && !(tmpaction instanceof BadRequest))
                return tmpaction;
        }

        return new BadRequest(chatId, messageText);
    }

    private String extractData(String messageText) {
        String[] words = messageText.split("\\s");
        StringBuilder result = new StringBuilder();
        if (words.length == 1)
            return messageText;
        else{
            for (int i = 1; i <= words.length - 1; i++) {
                result.append(words[i]);
            }
            return result.toString();
        }

    }

    private Action exactTeacherCase(String text) {
        String exactTeacherName = text.toUpperCase();
        Teacher teacher = teacherService.findByName(exactTeacherName);
        if (teacher != null){
            return makeAction(teacher);
        }
        else
            return notExactTeacherCase(exactTeacherName);
    }

    private Action notExactTeacherCase(String text) {
        String notExactTeacherName = text.toUpperCase();
        String searchTeacherLine = teacherService.getSearchLine().toUpperCase();
        notExactTeacherName = notExactTeacherName.replace("Ы", "И");

        String searchString = notExactTeacherName;

        for (int i = 0 ;
             searchString.length() > 4;
             searchString = notExactTeacherName.substring(0,notExactTeacherName.length()-i-1), i ++) {
            if (searchTeacherLine.contains(searchString)){
                Set<Object> teacherSet = new HashSet<>(teacherService.getByAlliesName(searchString));
                if (teacherSet.size() == 1) {
                    return makeAction(teacherSet.stream().findFirst().orElse(false));
                }
                return makeCollectAction(teacherSet);
            }
        }
        return new BadRequest(chatId, this.messageText);
    }

    private Action exactGroupCase(String text){
        String exactGroupName = normalize(text);
        Group group = groupService.getByExactName(exactGroupName);
        if (group != null){
            return makeAction(group);
        }
        else
            return notExactGroupCase(exactGroupName);
    }

    private Action notExactGroupCase(String text){
        String notExactGroupName = normalize(text);
        String searchGroupLine = groupService.getSearchLine();

        String searchString = notExactGroupName;

        for (int i = 0 ;
             searchString.length() >= 3;
             searchString = notExactGroupName.substring(0,notExactGroupName.length()-i-1), i ++) {

            if (searchGroupLine.contains(searchString)){
                Set<Object> groupSet = new HashSet<>(groupService.getByAlliesName(searchString));
                if (groupSet.size() == 1)
                    return makeAction(groupSet.stream().findFirst().orElse(false));
                return makeCollectAction(groupSet);
            }
        }
        return new BadRequest(chatId, this.messageText);

    }

    private Set<Date> dates;
    private Pattern numbericDayPattern = Pattern.compile("\\d\\d?(\\p{Punct}|\\s)\\d\\d?");
    private void parseDate(String messageText) {
        dates = new LinkedHashSet<>();
        parseNumericDate(messageText);
        parseDOWDate(messageText);
        parseOffsetDate(messageText);
        if (dates.isEmpty())
            dates.add(currdate);
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

    private boolean parseWeek(String messageText) {
        String weekLine = messageText.toLowerCase();
        if (weekLine.contains("nextweek")){
            currdate = DayOfWeek.weekDateOffset(currdate, 1);
            return true;
        }
        for (String s : new HashSet<>(Arrays.asList("тиждень", "неделя", "week"))) {
            if (weekLine.contains(s))
                return true;
        }
        return false;
    }

    private Action makeAction(Object o){
        if (reqiereWeek)
            return factory.sendWeekDataAction(o, chatId, currdate, 0);
        else
            return factory.sendDayDataAction(o, chatId, dates, 0);
    }

    private Action makeCollectAction(Collection<Object> collection){
        if (reqiereWeek)
            return factory.sendWeekDataAction(collection,chatId,0, messageText);
        else
            return factory.sendDayDataAction(collection,chatId, dates,0, messageText);
    }

    private String normalize(String initial){
        return StringUtils.replaceChars(initial, "Ии", "Іі").toUpperCase();
    }

    private boolean isOnlyCurrentDay() {
        return dates.size() == 1 && dates.stream().findFirst().orElse(null).equals(currdate);
    }



}
