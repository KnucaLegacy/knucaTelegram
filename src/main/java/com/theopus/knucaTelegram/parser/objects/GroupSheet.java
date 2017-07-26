package com.theopus.knucaTelegram.parser.objects;

import com.theopus.knucaTelegram.data.entity.Group;
import com.theopus.knucaTelegram.data.entity.enums.DayOfWeek;
import com.theopus.knucaTelegram.parser.ver20.ParserUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GroupSheet {

    private Group group;
    private Date titledDate;
    private Date minDate;
    private Date maxDate;

    private FilePDF filePDF;
    private String text;
    private Set<DayLessonListSheet> daylessons;

    private static final Pattern DAY_DOT_MONTH_PATTERN = Pattern.compile("(\\d?\\d)\\.(\\d?\\d)");
    private static final Pattern TITLE_DATE_PATTERN = Pattern.compile("(\\d?\\d)\\s([А-я]+)\\s(\\d{4})");
    private static final Pattern EXACT_GROUP_PATTERN = Pattern.compile("(академгрупа)\\s([А-яІіЇїЄє]{1,6}-\\S{1,6})");
    private static final Pattern TABLE_BOUNDS_PATTERN = Pattern.compile("=");

    GroupSheet(String textOfGroupSheet, FilePDF parentFile) {
        this.text = textOfGroupSheet;
        this.filePDF = parentFile;
    }

    public Set<DayLessonListSheet> parse(){
        group = parseGroup(text);
        String tableText = parseTableText(text);
        String noTableText = parseNoTableText(text);
        titledDate = parseTitledDate(text);
        minDate = parseMinDate(tableText, titledDate);
        maxDate = parseMaxDate(tableText, titledDate);
        return makeLessonListSheets(parseDayLessonsSheets(noTableText));
    }

    private String parseNoTableText(String text) {
        Matcher m = TABLE_BOUNDS_PATTERN.matcher(text);
        int index = 0;
        while (m.find()){
            index = m.end()+1;
        }
        return text.substring(index);
    }

    private Set<DayLessonListSheet> makeLessonListSheets(Map<DayOfWeek, String> map){
        Set<DayLessonListSheet> result = new LinkedHashSet<>();
        map.forEach((dayOfWeek, s) -> result.add(new DayLessonListSheet(dayOfWeek,s,this)));
        this.daylessons = result;
        return result;
    }

    private Map<DayOfWeek, String> parseDayLessonsSheets(String noTableText) {
        Map<Integer, DayOfWeek> map = new LinkedHashMap<>();
        Map<DayOfWeek,String> result = new LinkedHashMap<>();
        Pattern dayOfAWeek = Pattern.compile("" +
                "(Понеділок)|" +
                "(Вівторок)|" +
                "(Середа)|" +
                "(Четвер)|" +
                "(П'ятниця)|" +
                "(Субота)|" +
                "(Неділя)");
        Matcher m = dayOfAWeek.matcher(noTableText);

        while (m.find()){
            map.put(m.start(), ParserUtils.stringToDayOfWeek(m.group(0)));
            if (noTableText.substring(m.start(), m.end()).matches("$"))
                break;
        }

        int i = 0;
        int tmp = 0;
        DayOfWeek tmpDOW = null;
        for (Map.Entry<Integer, DayOfWeek> pair : map.entrySet()) {
            if (i == 0) {
                tmp = pair.getKey();
                tmpDOW = pair.getValue();
                i++;
                continue;
            }
            result.put(tmpDOW, noTableText.substring(tmp,pair.getKey()));
            tmp = pair.getKey();
            tmpDOW = pair.getValue();
            i++;
        }
        result.put(tmpDOW, noTableText.substring(tmp));

        return result;
    }

    private Date parseMaxDate(String tableText, Date titledDate) {
        Matcher m = DAY_DOT_MONTH_PATTERN.matcher(tableText);
        String day = "";
        String month = "";
        while (m.find()){
            day = m.group(1);
            month = m.group(2);
        }
        int startFind = tableText.lastIndexOf(month);
        int count = -2;
        for (int i = startFind; i < tableText.length(); i++) {
            String str = tableText.substring(i,i + 1);
            if (str.matches("\\|"))
                count++;
            if (str.matches("[^|.А-яAz\\d]"))
                break;
        }
        return DayOfWeek.dayDateOffset(ParserUtils.stringToDate(day,month,titledDate), count);
    }

    private Date parseMinDate(String tableText, Date titledDate) {
        Matcher m = DAY_DOT_MONTH_PATTERN.matcher(tableText);
        String day = "";
        String month = "";
        if (m.find()){
            day = m.group(1);
            month = m.group(2);
        }

        return ParserUtils.stringToDate(day,month,titledDate);
    }

    private Date parseTitledDate(String text){
        Matcher m = TITLE_DATE_PATTERN.matcher(text);
        int day = 0;
        int month = 0;
        int year = 0;
        if (m.find()){
            day = Integer.parseInt(m.group(1));
            month = ParserUtils.ukrMothToNumber(m.group(2));
            year = Integer.parseInt(m.group(3));
        }
        return new GregorianCalendar(year, month, day).getTime();
    }

    private Group parseGroup(String text){
        Matcher ma = EXACT_GROUP_PATTERN.matcher(text);
        String groupName = "";
        if (ma.find())
            groupName = ma.group(2);
        if (!groupName.isEmpty())
            return new Group(groupName);
        else
            return null;
    }

    private String parseTableText(String text){
        Matcher m = TABLE_BOUNDS_PATTERN.matcher(text);
        List<Integer> idexes = new ArrayList<>();

        while (m.find()) {
            idexes.add(m.start());
        }

        return text.substring(idexes.get(0),idexes.get(idexes.size()-1));
    }

    public Date getTitledDate() {
        return titledDate;
    }

    public Group getGroup() {
        return group;
    }

    public Date getMinDate() {
        return minDate;
    }

    public Date getMaxDate() {
        return maxDate;
    }

    public FilePDF getFilePDF() {
        return filePDF;
    }
}
