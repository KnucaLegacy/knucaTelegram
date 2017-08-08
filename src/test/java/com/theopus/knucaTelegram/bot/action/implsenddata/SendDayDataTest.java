package com.theopus.knucaTelegram.bot.action.implsenddata;

import com.theopus.knucaTelegram.config.PersistenceConfig;
import com.theopus.knucaTelegram.service.data.TeacherService;
import com.theopus.knucaTelegram.generictestclasses.GenericDBWithDBCheck;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;

@ContextConfiguration(classes = PersistenceConfig.class)
public class SendDayDataTest extends GenericDBWithDBCheck {
    @Test
    public void toS_tring() throws Exception {

    }

    @Resource
    TeacherService teacherService;

    @Test
    public void getCallBackQuery() throws Exception {

    }

    @Test
    public void buildMessage() throws Exception {
    }

}