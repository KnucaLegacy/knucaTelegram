package com.theopus.knucaTelegram.bot.util;

import com.theopus.knucaTelegram.data.entity.enums.DayOfWeek;

import java.util.*;

public class DayOffsetValues {

    private static Map<Integer, Set<String>> alliasMap = new HashMap<>();
    static {
        Set<String> zero = new HashSet<>(Arrays.asList("сегодня", "сьогодні", "зараз", "сейчас", "now", "today"));
        alliasMap.put(0, zero);
        Set<String> one = new HashSet<>(Arrays.asList("завтра", "завтр", "tomorrow"));
        alliasMap.put(1, one);
        Set<String> two = new HashSet<>(Arrays.asList("послезавтра", "післязавтра", "after tomorrow"));
        alliasMap.put(2, two);
        Set<String> minusone = new HashSet<>(Arrays.asList("вчера", "учора", "вчора", "yesterday"));
        alliasMap.put(-1, minusone);
        Set<String> minustwo = new HashSet<>(Arrays.asList("позавчера", "позавчора", "after yesterday"));
        alliasMap.put(-2, minustwo);

    }

    public static Integer whatOffset(String s){
        String normalized = s.toLowerCase();

        for (Integer integer : alliasMap.keySet()) {
            if (alliasMap.get(integer).contains(normalized))
                return integer;
        }
        return null;
    }
}
