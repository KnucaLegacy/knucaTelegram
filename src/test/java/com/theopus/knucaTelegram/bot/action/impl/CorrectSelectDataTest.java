package com.theopus.knucaTelegram.bot.action.impl;

import com.theopus.knucaTelegram.config.PersistenceConfig;
import com.theopus.knucaTelegram.config.TestPersistenceConfig;
import com.theopus.knucaTelegram.data.entity.Group;
import com.theopus.knucaTelegram.data.service.GroupService;
import com.theopus.knucaTelegram.generictestclasses.GenericDBWithDBCheck;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.PropertySources;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;


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
        Object o = set.stream().findFirst().orElse(null);
        CorrectSelectData correctSelectData = new CorrectSelectData(new HashSet<>(set), new SendDayData(0, null, o, null, 0));
        correctSelectData.buildMessage();
    }

}