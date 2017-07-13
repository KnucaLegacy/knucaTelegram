package com.theopus.knucaTelegram.bot;

import com.theopus.knucaTelegram.config.PersistenceConfig;
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
import java.util.Date;
import java.util.GregorianCalendar;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestPersistenceConfig.class, MessageEventDispatcher.class})
//@SpringBootTest
public class MessageEventDispatcherTest {


    @Resource
    private EntityManagerFactory emf;
    protected EntityManager em;


    @Qualifier("messageEventDispatcher")
    @Autowired
    private MessageEventDispatcher messageEventDispatcher;



    private Date dateMock = new GregorianCalendar(2017, 4 - 1, 17).getTime();
    @Autowired
    private GroupService groupService;

    @Before
    public void setUp() throws Exception {
        em = emf.createEntityManager();
    }

    @Test
    public void testGroupCase() throws Exception {
        messageEventDispatcher.handleMessage("іуст-32", 7357).execute();
    }
}