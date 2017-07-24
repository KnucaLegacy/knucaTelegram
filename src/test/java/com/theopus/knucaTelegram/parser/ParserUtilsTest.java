package com.theopus.knucaTelegram.parser;

import com.theopus.knucaTelegram.data.entity.enums.DayOfWeek;
import org.junit.Test;

import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

public class ParserUtilsTest {
    @Test
    public void minDateOffser() throws Exception {

        Date time = new GregorianCalendar(2017, 3 - 1, 23).getTime();
        System.out.println(time);
        System.out.println(ParserUtils.minDateOffser(time, DayOfWeek.FRIDAY));
    }

    @Test
    public void maxDateOffset() throws Exception {
        Date time = new GregorianCalendar(2017, 5 - 1, 24).getTime();
        System.out.println(time);
        System.out.println(ParserUtils.maxDateOffser(time, DayOfWeek.THURSDAY));
    }
}