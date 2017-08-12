package com.theopus.knucaTelegram.parser;

import com.theopus.knucaTelegram.entity.schedule.enums.DayOfWeek;
import com.theopus.knucaTelegram.parser.core.ParserUtils;
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
        Date from = new GregorianCalendar(2017, 3 - 1, 24).getTime();
        Date to = new GregorianCalendar(2017, 4 - 1, 28).getTime();
        System.out.println(from);
        System.out.println(to);
        System.out.println(ParserUtils.fromToToDatesSet(from,to,DayOfWeek.FRIDAY));

    }

    @Test
    public void afTest() throws Exception {
        Date from = new GregorianCalendar(2017, 4 - 1, 12).getTime();
        Date to = new GregorianCalendar(2017, 5 - 1, 10).getTime();
        System.out.println(from);
        System.out.println(to);
        System.out.println(ParserUtils.fromToToDatesSetAF(from,to,DayOfWeek.WEDNESDAY));
    }

    @Test
    public void minDateOffset() throws Exception {
        Date from = new GregorianCalendar(2017, 4 - 1, 12).getTime();
        Date to = new GregorianCalendar(2017, 5 - 1, 10).getTime();
//        System.out.println(ParserUtils.minDateOffser());
    }
}