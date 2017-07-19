package com.theopus.knucaTelegram.bot;

import com.theopus.knucaTelegram.bot.command.TelegramMessageFormater;
import com.theopus.knucaTelegram.config.TestPersistenceConfig;
import com.theopus.knucaTelegram.data.service.GroupService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        TestPersistenceConfig.class,
        MessageActionDispatcher.class,
        TelegramMessageFormater.class})
//@SpringBootTest
public class MessageActionDispatcherTest {


    @Resource
    private EntityManagerFactory emf;
    protected EntityManager em;


    @Qualifier("messageActionDispatcher")
    @Autowired
    private MessageActionDispatcher messageActionDispatcher;





    private Date dateMock = new GregorianCalendar(2017, 4 - 1, 17).getTime();
    @Autowired
    private GroupService groupService;

    @Before
    public void setUp() throws Exception {
        em = emf.createEntityManager();
    }


    @Test
    public void test() throws Exception {
        System.out.println(messageActionDispatcher.groupsList.contains("ІУС"));
    }

    @Test
    public void testfactory() throws Exception {

    }
}