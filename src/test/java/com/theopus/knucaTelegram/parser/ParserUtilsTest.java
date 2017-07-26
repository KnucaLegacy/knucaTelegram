package com.theopus.knucaTelegram.parser;

import com.theopus.knucaTelegram.data.entity.enums.DayOfWeek;
import com.theopus.knucaTelegram.parser.ver20.ParserUtils;
import org.junit.Test;

import java.util.Date;
import java.util.GregorianCalendar;

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
        System.out.println(ParserUtils.maxDateOffser(time, DayOfWeek.MONDAY));
    }

    @Test
    public void fromToToDateSet() throws Exception {
        Date from = new GregorianCalendar(2017, 3 - 1, 21).getTime();
        Date to = new GregorianCalendar(2017, 4 - 1, 11).getTime();
        System.out.println(from);
        System.out.println(to);
        System.out.println(ParserUtils.fromToToDatesSet(from,to,DayOfWeek.TUESDAY));

    }
}