package com.theopus.knucaTelegram.data.entity.enums;

import com.theopus.knucaTelegram.parser.ParserPDF;
import org.junit.Test;

import java.util.Date;
import java.util.Map;

import static org.junit.Assert.*;

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
}