package com.theopus.knucaTelegram.data.entity.enums;

import com.theopus.knucaTelegram.entity.schedule.enums.DayOfWeek;
import org.junit.Test;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

public class DayOfWeekTest {


    @Test
    public void dateToRawDate() throws Exception {
        Date start = new Date();
        System.out.println(start);
        Date finish = DayOfWeek.dateToRawDate(start);
        System.out.println(finish);
    }

    @Test
    public void dateMapTest() throws Exception {
        Date date = new Date();
        System.out.println(date);
        for (Map.Entry key : DayOfWeek.dateToDateMap(date).entrySet()) {
            System.out.println(key.getKey() + " - " + key.getValue());
        }
    }

    @Test
    public void fromToTest() throws Exception {
        Date from = new GregorianCalendar(2017, 5 - 1, 22).getTime();
        Date to = new GregorianCalendar(2017, 5 - 1, 26).getTime();
        DayOfWeek.fromToToDateSet(from, to).forEach(System.out::println);
    }

    @Test
    public void dateToWeek() throws Exception {
        DayOfWeek.getWeekOfDate(new Date()).forEach(System.out::println);
    }
}