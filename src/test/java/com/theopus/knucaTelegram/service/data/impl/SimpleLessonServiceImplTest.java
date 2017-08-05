package com.theopus.knucaTelegram.service.data.impl;

import com.theopus.knucaTelegram.bot.util.TelegramMessageFormater;
import com.theopus.knucaTelegram.entity.Group;
import com.theopus.knucaTelegram.generictestclasses.GenericDBWithDBCheck;
import com.theopus.knucaTelegram.service.data.SimpleLessonService;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

/**
 * Created by irina on 04.08.17.
 */
public class SimpleLessonServiceImplTest extends GenericDBWithDBCheck {
    @Resource
    private SimpleLessonService simpleLessonService;
    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void singleDayByGroup() throws Exception {
        Date singleDate = new GregorianCalendar(2017, 4-1, 17).getTime();
        Date secondDate = new GregorianCalendar(2017, 4-1, 22).getTime();
        Group group = groupService.getById(1);

        TelegramMessageFormater formater = new TelegramMessageFormater();

        formater.toDaysMessage(simpleLessonService.getByGroup(singleDate,secondDate, group), group).forEach(System.out::println);

    }
}