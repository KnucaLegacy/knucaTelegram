package com.theopus.knucaTelegram.data.service.impl;

import com.theopus.knucaTelegram.config.PersistenceConfig;
import com.theopus.knucaTelegram.config.TestPersistenceConfig;
import com.theopus.knucaTelegram.data.repository.GroupRepository;
import com.theopus.knucaTelegram.data.service.LessonService;
import com.theopus.knucaTelegram.data.service.TeacherService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestPersistenceConfig.class)
@WebAppConfiguration
public class GroupServiceImplTest {

    @Resource
    private GroupRepository groupRepository;

    @Resource
    private EntityManagerFactory emf;
    protected EntityManager em;

    private Date dateMock = new GregorianCalendar(2017, 4 - 1, 17).getTime();

    @Before
    public void setUp() throws Exception {
        em = emf.createEntityManager();
    }

    @Test
    public void name() throws Exception {
        groupRepository.findNameAliesPaged("арх",new PageRequest(1, 5)).forEach(group -> System.out.println(group));
    }
}