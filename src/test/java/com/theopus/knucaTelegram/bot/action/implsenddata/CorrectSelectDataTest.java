package com.theopus.knucaTelegram.bot.action.implsenddata;

import com.theopus.knucaTelegram.config.PersistenceConfig;
import com.theopus.knucaTelegram.data.entity.Group;
import com.theopus.knucaTelegram.generictestclasses.GenericDBWithDBCheck;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

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
        Object o = set.stream().findFirst().orElse(null);
        CorrectSelectData correctSelectData = new CorrectSelectData(new HashSet<>(set), new SendDayData(0, null, o, null, 0), "хуй");
        correctSelectData.buildMessage();
    }

}