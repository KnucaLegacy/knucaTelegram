package com.theopus.knucaTelegram.data.entity.enums;

import java.util.*;


public enum DayOfWeek {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;

    public static DayOfWeek intToDay(int i){
        switch (i){
            case 0: return MONDAY;
            case 1: return TUESDAY;
            case 2: return WEDNESDAY;
            case 3: return THURSDAY;
            case 4: return FRIDAY;
            case 5: return SATURDAY;
            default: return SUNDAY;
        }
    }

    public static String toReadable(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY: return "Понедельник";
            case TUESDAY: return "Вторние";
            case WEDNESDAY: return "Среда";
            case THURSDAY: return "Четверг";
            case FRIDAY: return "Пятница";
            case SATURDAY: return "Суббота";
            case SUNDAY: return "Воскресенье";
            default: return null;
        }
    }

    public static DayOfWeek calendarDOWtoDayOfWeek(int calendarDOW){
       switch (calendarDOW){
           case Calendar.MONDAY: return DayOfWeek.MONDAY;
           case Calendar.TUESDAY: return DayOfWeek.TUESDAY;
           case Calendar.WEDNESDAY: return DayOfWeek.WEDNESDAY;
           case Calendar.THURSDAY: return DayOfWeek.THURSDAY;
           case Calendar.FRIDAY: return DayOfWeek.FRIDAY;
           case Calendar.SATURDAY: return DayOfWeek.SATURDAY;
           case Calendar.SUNDAY: return DayOfWeek.SUNDAY;
           default: return null;
       }
    }

    public static DayOfWeek dateToDayOfWeek(Date date){
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        return calendarDOWtoDayOfWeek(gregorianCalendar.get(Calendar.DAY_OF_WEEK));
    }

    public static Date dateToRawDate(Date date){
        GregorianCalendar initialGC = new GregorianCalendar();
        initialGC.setTime(date);
        GregorianCalendar resultGc = new GregorianCalendar(
                initialGC.get(Calendar.YEAR),
                initialGC.get(Calendar.MONTH),
                initialGC.get(Calendar.DAY_OF_MONTH));
        return resultGc.getTime();
    }

    public static Map<DayOfWeek, Date> dateToDateMap(Date date){
        int dayOfWeek = dateToDayOfWeek(date).ordinal();
        Date initDate = dateToRawDate(date);
        Map<DayOfWeek, Date> result = new TreeMap<>();
        Date dat = null;
        long oneDay = 1 * 1000 * 3600 * 24;
        for (int i = 0; i < 7; i++) {
            dat = new Date(initDate.getTime() - ((dayOfWeek - i) * oneDay));
            result.put(intToDay(i), dat);
        }

        return result;
    }

    public static Date weekDateOffset(Date date, int offset){
        long oneWeek = 1 * 1000 * 3600 * 24 * 7;
        return new Date(date.getTime() + oneWeek * offset);
    }

    public static Date dayDateOffset(Date date, int offset){
        long oneDay = 1 * 1000 * 3600 * 24;
        return new Date(dateToRawDate(date).getTime() + oneDay * offset);
    }
}
