package com.theopus.knucaTelegram.parser.core;

import com.theopus.knucaTelegram.entity.enums.DayOfWeek;
import com.theopus.knucaTelegram.entity.enums.LessonOrder;

import java.util.*;
import java.util.regex.Pattern;

public class ParserUtils {

    public static DayOfWeek stringToDayOfWeek(String s){
        Pattern dayOfAWeek = Pattern.compile("" +
                "([^|]Понеділок)|" +
                "([^|]Вівторок)|" +
                "([^|]Середа)|" +
                "([^|]Четвер)|" +
                "([^|]П'ятниця)" +
                "|([^|]Субота)");
        switch (s){
            case "Понеділок":
                return DayOfWeek.MONDAY;
            case "Вівторок":
                return DayOfWeek.TUESDAY;
            case "Середа":
                return DayOfWeek.WEDNESDAY;
            case "Четвер":
                return DayOfWeek.THURSDAY;
            case "П'ятниця":
                return DayOfWeek.FRIDAY;
            case "Субота":
                return DayOfWeek.SATURDAY;
            default:
                return DayOfWeek.SUNDAY;
        }
    }

    public static LessonOrder timeToOrder(String string){
        switch (string){
            case "9:00": return LessonOrder.FIRST;
            case "10:30": return LessonOrder.SECOND;
            case "12:20": return LessonOrder.THIRD;
            case "13:50": return LessonOrder.FOURTH;
            case "15:20": return LessonOrder.FIFTH;
            case "16:50": return LessonOrder.SIXTH;
            case "18:20": return LessonOrder.SEVENTH;
            default: return LessonOrder.OUT_OF_SCHEDULE;
        }
    }

    public static void printMap(Map<String,String> map){
        for (Map.Entry<String, String> pair: map.entrySet()) {
            System.out.println(pair.getKey() + " " + pair.getValue());
        }
    }

    public static void print(Collection strings){
        for (Object o :
                strings) {
            System.out.println(o);
        }
    }

    public static int ukrMothToNumber(String month){
        month = month.toLowerCase();
        switch (month){
            case "січня": return 0;
            case "лютого": return 1;
            case "березня": return 2;
            case "квітня": return 3;
            case "травня": return 4;
            case "червня": return 5;
            case "липня": return 6;
            case "серпня": return 7;
            case "вересня": return 8;
            case "жовтня": return 9;
            case "листопада": return 10;
            case "грудня": return 11;
            default:return 0;
        }
    }

    public static Date stringToDate(String dayS, String monthS, Date yearD){
        int day = Integer.parseInt(dayS);
        int month = Integer.parseInt(monthS);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(yearD);
        int year = calendar.get(Calendar.YEAR);
        return new GregorianCalendar(year, month - 1, day).getTime();
    }

    public static Date stringToDate(String daySmonthS, Date yearD){
        return stringToDate(daySmonthS.split("\\.")[0], daySmonthS.split("\\.")[1], yearD);
    }

    public static Date dayOffset(Date date, DayOfWeek dayOfWeek){
        return DayOfWeek.dayOfweekToDate(dayOfWeek, date);
    }

    public static Date minDateOffser(Date minDate, DayOfWeek dayOfWeek){
        Map<DayOfWeek, Date> dayOfWeekDateMap = DayOfWeek.dateToDateMap(minDate);
        if (dayOfWeekDateMap.get(dayOfWeek).getTime() < minDate.getTime())
            return DayOfWeek.weekDateOffset(dayOfWeekDateMap.get(dayOfWeek), 1);
        else
            return dayOfWeekDateMap.get(dayOfWeek);
    }

    public static Date maxDateOffser(Date maxDate, DayOfWeek dayOfWeek){
        Map<DayOfWeek, Date> dayOfWeekDateMap = DayOfWeek.dateToDateMap(maxDate);
        if (dayOfWeekDateMap.get(dayOfWeek).getTime() > maxDate.getTime())
            return DayOfWeek.weekDateOffset(dayOfWeekDateMap.get(dayOfWeek), -1);
        else
            return dayOfWeekDateMap.get(dayOfWeek);
    }

    public static Set<Date> fromToToDatesSet(Date from, Date to, DayOfWeek dow){
        Set<Date> result = new LinkedHashSet<>();
        long diff = DayOfWeek.dateToRawDate(to).getTime() - DayOfWeek.dateToRawDate(from).getTime();
        if (diff <= 0)
            diff = DayOfWeek.dateToRawDate(from).getTime() - DayOfWeek.dateToRawDate(to).getTime();
        int days = (int) (diff / 1000 / 3600 / 24);
        for (int i = 0; i <= days + 1; i++) {
            Date date = DayOfWeek.dayDateOffset(from, i);
            if (DayOfWeek.dateToDayOfWeek(date).equals(dow))
                result.add(date);
        }
        return result;

    }
}
