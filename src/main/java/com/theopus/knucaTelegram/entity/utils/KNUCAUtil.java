package com.theopus.knucaTelegram.entity.utils;



import com.theopus.knucaTelegram.entity.enums.LessonOrder;

import java.util.Collection;
import java.util.Map;

public class KNUCAUtil {

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
}
