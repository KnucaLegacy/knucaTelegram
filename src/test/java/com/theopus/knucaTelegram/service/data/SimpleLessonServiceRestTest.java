package com.theopus.knucaTelegram.service.data;

import com.theopus.knucaTelegram.config.JacksonConfig;
import com.theopus.knucaTelegram.entity.schedule.Group;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SimpleLessonServiceRest.class, JacksonConfig.class})
public class SimpleLessonServiceRestTest {

    @Autowired
    private SimpleLessonService simpleLessonServiceRest;

    @Test
    public void getByGroup() throws Exception {
        Group group = new Group();
        group.setId(1L);
        LocalDate localDate = LocalDate.of(2017, 9, 8);
        Date date = Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        System.out.println(simpleLessonServiceRest.getByGroup(date, group));
    }

    @Test
    public void getByTeacher() throws Exception {
        LocalDate startDate = LocalDate.of(2017, 8, 9);
        LocalDate endDate = LocalDate.of(2017, 8, 12);

    }

}