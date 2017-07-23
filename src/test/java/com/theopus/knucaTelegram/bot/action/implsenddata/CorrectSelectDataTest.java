package com.theopus.knucaTelegram.bot.action.implsenddata;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.theopus.knucaTelegram.config.PersistenceConfig;
import com.theopus.knucaTelegram.data.entity.Group;
import com.theopus.knucaTelegram.generictestclasses.GenericDBWithDBCheck;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;

import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@ContextConfiguration(classes = PersistenceConfig.class)
public class CorrectSelectDataTest extends GenericDBWithDBCheck{


    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void buildMessage() throws Exception {
        Set<Group> set = groupService.getByAlliesName("іуст");
        Object o = null;
        CorrectSelectData correctSelectData = new CorrectSelectData(new HashSet<>(set), new SendDayData(0, null, o, null), "хуй");
        correctSelectData.buildMessage();
    }

    @Test
    public void jsontest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Hibernate5Module());

        Group group = groupService.getById(2);
        System.out.println("from code" + group);
        objectMapper.writeValue(new File("group"), group);
        String s = objectMapper.writeValueAsString(group);
        System.out.println("json " + s);
//        new SendDayData(0,lessonService, group, new Date(), 0);
    }
}