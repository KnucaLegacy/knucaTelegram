package com.theopus.knucaTelegram.service.data.impl;

import com.theopus.knucaTelegram.entity.schedule.Group;
import com.theopus.knucaTelegram.entity.schedule.Lesson;
import com.theopus.knucaTelegram.generictestclasses.GenericDBWithDBCheck;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import stubs.LessonStubs;

import java.util.*;

public class GroupServiceImplTest extends GenericDBWithDBCheck {


    private Date dateMock = new GregorianCalendar(2017, 4 - 1, 17).getTime();
    private Set<Group> groupsCache = new HashSet<>();

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void getAlliasesNull() throws Exception {
        List<Group> forload = new ArrayList<Group>(){
            {
                add(new Group("Grsoup1"));
                add(new Group("Grousp2"));
                add(new Group("Gfaroup3"));
                add(new Group("Grousp4"));
                add(new Group("Groasdup5"));
            }
        };
        Set<Group> result = groupService.saveAll(forload);
        Assert.assertTrue(groupService.getByAlliesName("Group").isEmpty());

        forload.forEach(groupService::deleteBy);
        groupService.flush();
    }

    @Test
    public void getAlliases() throws Exception {
        List<Group> forload = new ArrayList<Group>(){
            {
                add(new Group("Grsoup1"));
                add(new Group("Group2"));
                add(new Group("Gfaroup3"));
                add(new Group("Group4"));
                add(new Group("Grsoup5"));
            }
        };
        Set<Group> groups1 = groupService.saveAll(forload);
        Assert.assertEquals(2, groupService.getByAlliesName("Gro").size());

        forload.forEach(groupService::deleteBy);
        groupService.flush();
    }

    @Test
    public void getByExactName() throws Exception {
        Group gr1 = new Group();
        gr1.setName("Group1");
        Group saved = groupService.saveOne(gr1);
        Group loaded = groupService.getByExactName("Group1");

        Assert.assertEquals(saved, loaded);

        groupService.flush();
        groupService.deleteBy(gr1);
    }

    @Test
    public void getCount() throws Exception {
        Set<Group> expexcted = new HashSet<Group>(){
            {
                add(new Group("Group1"));
                add(new Group("Group2"));
                add(new Group("Group3"));
                add(new Group("Group4"));
            }
        };

        Set<Group> loaded = groupService.saveAll(expexcted);
        Assert.assertEquals(4, groupService.getCount());

        loaded.forEach(groupService::deleteBy);
        groupService.flush();
    }

    @Test
    public void deleteById() throws Exception {
        Group gr1 = new Group();
        gr1.setName("Group1");
        gr1.setLessons(new HashSet<Lesson>(){
            {
                List<Lesson> lessons = LessonStubs.makeList(new Date());
                lessons.forEach(lesson -> lesson.getGroups().add(gr1));
                this.addAll(lessons);
            }
        });
        Group saved = groupService.saveOne(gr1);
        groupService.flush();
        groupService.deleteBy(saved.getId());
        Assert.assertEquals(0,groupService.getAll().size());
    }

    @Test
    public void saveOneAndGet() throws Exception {
        Group gr1 = new Group();
        gr1.setName("Group1");
        Group saved = groupService.saveOne(gr1);
        Group loaded = groupService.getById(saved.getId());
        groupService.flush();
        Assert.assertEquals(saved, loaded);

        groupService.deleteBy(gr1);

    }

    @Test
    public void saveAllTest() throws Exception {
        Set<Group> expexcted = new HashSet<Group>(){
            {
                add(new Group("Group1"));
                add(new Group("Group2"));
                add(new Group("Group3"));
                add(new Group("Group4"));
            }
        };

        Set<Group> loaded = groupService.saveAll(expexcted);

        Assert.assertEquals(expexcted, loaded);

        loaded.forEach(groupService::deleteBy);
        groupService.flush();

    }

    @Test
    public void sameSaveAll() throws Exception {
        List<Group> forload = new ArrayList<Group>(){
            {
                add(new Group("Group"));
                add(new Group("Group"));
                add(new Group("Group"));
                add(new Group("Group"));
                add(new Group("Group"));
            }
        };
        Set<Group> groups1 = groupService.saveAll(forload);
        Assert.assertEquals(1, groups1.size());

        forload.forEach(groupService::deleteBy);
        groupService.flush();

    }

    @Test
    public void saveSameWithOutCache() throws Exception {
        List<Group> forload = new ArrayList<Group>(){
            {
                add(new Group("Group"));
                add(new Group("Group"));
                add(new Group("Group"));
                add(new Group("Group"));
                add(new Group("Group"));
            }
        };

        forload.forEach(group -> {
            groupService.saveOne(group);
            groupService.flush();
        });
        Assert.assertEquals(1, groupService.getAll().size());

        forload.forEach(groupService::deleteBy);
        groupService.flush();
    }

    @Test
    public void getById() throws Exception {
        groupsCache.forEach(group -> {
            Assert.assertEquals(group, groupService.getById(group.getId()));
        });
    }

    @After
    public void tearDown() throws Exception {
        groupsCache.forEach(groupService::deleteBy);
        groupService.getAll().forEach(groupService::deleteBy);
        groupService.flush();
    }
}