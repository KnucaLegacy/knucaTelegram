package com.theopus.knucaTelegram.bot.action.implsenddata;

import com.theopus.knucaTelegram.config.PersistenceConfig;
import com.theopus.knucaTelegram.data.entity.Group;
import com.theopus.knucaTelegram.data.entity.Teacher;
import com.theopus.knucaTelegram.data.service.TeacherService;
import com.theopus.knucaTelegram.generictestclasses.GenericDBWithDBCheck;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import java.util.Date;

@ContextConfiguration(classes = PersistenceConfig.class)
public class SendDayDataTest extends GenericDBWithDBCheck {
    @Test
    public void toS_tring() throws Exception {

    }

    @Resource
    TeacherService teacherService;

    @Test
    public void getCallBackQuery() throws Exception {
        long id = 0;
        Teacher teacher = teacherService.getById(1);
        String s = new SendDayData(id, lessonService, teacher, new Date()).getCallBackQuery();
        System.out.println(s);

    }

    @Test
    public void buildMessage() throws Exception {
    }

}