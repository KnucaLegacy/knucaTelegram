package com.theopus.knucaTelegram.service.data.impl;

import com.theopus.knucaTelegram.entity.schedule.Group;
import com.theopus.knucaTelegram.generictestclasses.GenericDBWithDBCheck;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class SearchLineTest extends GenericDBWithDBCheck {

    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void groupSearchLine() throws Exception {
        Set<Group> expexcted = new HashSet<Group>(){
            {
                add(new Group("Group1"));
                add(new Group("Group2"));
                add(new Group("Group3"));
                add(new Group("Group4"));
            }
        };
        Set<Group> loaded = groupService.saveAll(expexcted);
        groupService.flush();
        Assert.assertEquals(";Group1;Group2;Group3;Group4;",groupService.getSearchLine());
        loaded.forEach(groupService::deleteBy);
    }
}
