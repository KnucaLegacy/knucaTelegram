package com.theopus.knucaTelegram.bot.util;

import com.theopus.knucaTelegram.data.entity.enums.DayOfWeek;

import java.util.*;

public class DayOfWeekAllias {

    private static Map<DayOfWeek, Set<String>> alliasMap = new HashMap<>();

    static {
        Set<String> monday = new HashSet<>(Arrays.asList("понедельник", "пн", "понеділок", "пон", "mon", "monday"));
        alliasMap.put(DayOfWeek.MONDAY, monday);
        Set<String> tuesday = new HashSet<>(Arrays.asList("вторник","вт","втор","вівторок","tue", "tuesday"));
        alliasMap.put(DayOfWeek.TUESDAY, monday);
        Set<String> wednesday = new HashSet<>(Arrays.asList("среда","ср","сред","сер","середа","серд","wed","wednesday"));
        alliasMap.put(DayOfWeek.WEDNESDAY, monday);
        Set<String> thursday = new HashSet<>(Arrays.asList("четверг","чт","четв","четвер","thu","thursday"));
        alliasMap.put(DayOfWeek.THURSDAY, monday);
        Set<String> friday = new HashSet<>(Arrays.asList("пятница","пт","пятниця","п'ятниця","п*ятниця", "fri","friday"));
        alliasMap.put(DayOfWeek.FRIDAY, monday);
        Set<String> saturday = new HashSet<>(Arrays.asList("суббота","сб","субота","sat","saturday","суб"));
        alliasMap.put(DayOfWeek.SATURDAY, monday);
        Set<String> sunday = new HashSet<>(Arrays.asList("воскрес","воскресенье","вс","нд","неділя"));
        alliasMap.put(DayOfWeek.SUNDAY, monday);
    }

    public static DayOfWeek whatDayOfWeek(String s){
        String normalized = s.toLowerCase();
        for (DayOfWeek dayOfWeek : alliasMap.keySet()) {
            if (alliasMap.get(dayOfWeek).contains(normalized))
                return dayOfWeek;
        }
        return null;
    }



}
