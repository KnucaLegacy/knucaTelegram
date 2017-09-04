package com.theopus.knucaTelegram.service.data.impl;

import com.theopus.knucaTelegram.KnucaTelegramApplication;
import com.theopus.knucaTelegram.bot.util.TelegramMessageFormater;
import com.theopus.knucaTelegram.config.PersistenceConfig;
import com.theopus.knucaTelegram.entity.schedule.Group;
import com.theopus.knucaTelegram.entity.schedule.Lesson;
import com.theopus.knucaTelegram.generictestclasses.GenericDBWithDBCheck;
import com.theopus.knucaTelegram.parser.core.MainParser;
import com.theopus.knucaTelegram.service.data.SimpleLessonService;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;

/**
 * Created by irina on 04.08.17.
 */
public class SimpleLessonServiceImplTest extends GenericDBWithDBCheck {


    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void singleDayByGroup() throws Exception {

    }

    @Test
    public void loadAll() throws Exception {

    }


    @Test
    public void get() throws Exception {

    }
}